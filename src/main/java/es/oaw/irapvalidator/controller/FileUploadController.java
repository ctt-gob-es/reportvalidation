package es.oaw.irapvalidator.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.model.ResponseValidatedFile;
import es.oaw.irapvalidator.model.ValidatedFile;
import es.oaw.irapvalidator.storage.FileDbStorageService;
import es.oaw.irapvalidator.validator.OdsValidator;
import es.oaw.irapvalidator.validator.ValidationError;
import es.oaw.irapvalidator.validator.XlsxValidator;

/**
 * The Class FileUploadController.
 */
@Controller
@RequestMapping(path = Constants.UPLOAD_CONTROLLER_PATH)
public class FileUploadController {

	/** The storage service. */
	@Autowired
	private FileDbStorageService storageService;

	/** The ods validator. */
	@Autowired
	private OdsValidator odsValidator;

	/** The xlsx validator. */
	@Autowired
	private XlsxValidator xlsxValidator;

	/** The tmp path. */
	String TMP_PATH = "/tmp/targetFile.tmp";

	/**
	 * List uploaded files.
	 *
	 * @param model the model
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping
	public String listUploadedFiles(Model model) throws IOException {
		List<ResponseValidatedFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId()).toUriString();

			ResponseValidatedFile fileResponse = new ResponseValidatedFile(dbFile.getName(), fileDownloadUri,
					dbFile.getType(), dbFile.getData().length);

			return fileResponse;
		}).collect(Collectors.toList());

		model.addAttribute("files", files);
		model.addAttribute("content", "../fragments/files/" + Constants.FRAGMENT_UPLOAD_TABLE);
		return Constants.TEMPLATE_LOGGED_IN;
	}

	/**
	 * Serve file.
	 *
	 * @param filename the filename
	 * @return the response entity
	 */
	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
		ValidatedFile file = storageService.getFile(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.body(file.getData());
	}

	/**
	 * Handle file upload.
	 *
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @param model              the model
	 * @return the string
	 */
	@PostMapping("/")
	public String handleFileUpload(MultipartHttpServletRequest request, RedirectAttributes redirectAttributes,
			Model model) {

		Map<String, MultipartFile> fileMap = request.getFileMap();
		List<String> validFiles = new ArrayList<>();
		List<ErrorInfo> invalidFiles = new ArrayList<>();

		for (MultipartFile file : fileMap.values()) {
			try {
				String filename = file.getOriginalFilename();
				String ext = filename.substring(filename.lastIndexOf(".") + 1);
				Map<String, List<ValidationError>> errors = new HashMap();

				switch (ext) {
				case "ods":

					try {
						// we need to write the file to disk in order to read it as ods...
						InputStream inputStream = file.getInputStream();
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						File targetFile = new File(TMP_PATH);
						OutputStream outStream = new FileOutputStream(targetFile);
						outStream.write(buffer);
						final SpreadSheet spreadSheet = SpreadSheet.createFromFile(targetFile);
						errors = odsValidator.validate(spreadSheet);
						targetFile.delete();
						outStream.close();
					} catch (Exception e) {
						throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
					}
					break;
				case "xlsx":
					XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
					errors = xlsxValidator.validate(workbook);
					break;
				}

				if (errors != null) {

					boolean hasErrors = false;
					for (Map.Entry<String, List<ValidationError>> entry : errors.entrySet()) {
						if (entry.getValue() != null && !entry.getValue().isEmpty()) {
							hasErrors = true;
							break;
						}

					}

					if (hasErrors) {
						invalidFiles.add(new ErrorInfo(filename, errors));
					} else {
						validFiles.add(file.getOriginalFilename());
					}
				} else {
					validFiles.add(file.getOriginalFilename());
					// storageService.store(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("valid", validFiles);
		model.addAttribute("invalid", invalidFiles);
		model.addAttribute("content", "../fragments/files/" + Constants.FRAGMENT_UPLOAD_SUMMARY);
		return Constants.TEMPLATE_LOGGED_IN;
	}

	/**
	 * * Temporary until proper validation is implemented.
	 */
	public static class ErrorInfo {

		/** The filename. */
		private final String filename;

		/** The errors. */
		private final Map<String, List<ValidationError>> errors;

		/**
		 * Instantiates a new error info.
		 *
		 * @param filename the filename
		 * @param errors   the errors
		 */
		public ErrorInfo(String filename, Map<String, List<ValidationError>> errors) {
			this.filename = filename;
			this.errors = errors;
		}

		/**
		 * Gets the filename.
		 *
		 * @return the filename
		 */
		public String getFilename() {
			return this.filename;
		}

		/**
		 * Gets the errors.
		 *
		 * @return the errors
		 */
		public Map<String, List<ValidationError>> getErrors() {
			return this.errors;
		}
	}
}
