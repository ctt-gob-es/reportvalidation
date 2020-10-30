package es.oaw.irapvalidator.controller;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.model.ResponseValidatedFile;
import es.oaw.irapvalidator.model.ValidatedFile;
import es.oaw.irapvalidator.storage.FileDbStorageService;
import es.oaw.irapvalidator.validator.OdsValidator;
import es.oaw.irapvalidator.validator.ValidationError;
import es.oaw.irapvalidator.validator.XlsxValidator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = Constants.UPLOAD_CONTROLLER_PATH)
public class FileUploadController {

	@Autowired
	private FileDbStorageService storageService;

	@Autowired
	private OdsValidator odsValidator;

	@Autowired
	private XlsxValidator xlsxValidator;

	String TMP_PATH = "src/main/resources/targetFile.tmp";

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		List<ResponseValidatedFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder
					.fromCurrentContextPath()
					.path("/files/")
					.path(dbFile.getId())
					.toUriString();

			ResponseValidatedFile fileResponse = new ResponseValidatedFile(
					dbFile.getName(),
					fileDownloadUri,
					dbFile.getType(),
					dbFile.getData().length);

			return fileResponse;
		}).collect(Collectors.toList());

		model.addAttribute("files", files);
		model.addAttribute("content", "../fragments/" + Constants.FRAGMENT_UPLOAD_TABLE);
		return Constants.TEMPLATE_DRAG_UPLOAD;
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
		ValidatedFile file = storageService.getFile(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.body(file.getData());
	}

	@PostMapping("/")
	public String handleFileUpload(MultipartHttpServletRequest request,
								   RedirectAttributes redirectAttributes, Model model) {

		Map<String, MultipartFile> fileMap = request.getFileMap();
		List<String> validFiles = new ArrayList<>();
		List<ErrorInfo> invalidFiles = new ArrayList<>();

		for (MultipartFile file : fileMap.values()) {
			try {
				String filename = file.getOriginalFilename();
				String ext = filename.substring(filename.lastIndexOf(".") + 1);
				List<ValidationError> errors = new ArrayList<>();

				switch (ext){
					case "ods":

						try {
							//we need to write the file to disk in order to read it as ods...
							InputStream inputStream = file.getInputStream();
							byte[] buffer = new byte[inputStream.available()];
							inputStream.read(buffer);
							File targetFile = new File(TMP_PATH);
							OutputStream outStream = new FileOutputStream(targetFile);
							outStream.write(buffer);
							final SpreadSheet spreadSheet = SpreadSheet.createFromFile(targetFile);
							errors = odsValidator.validate(spreadSheet);
							targetFile.delete();
						} catch (Exception e) {
							throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
						}
						break;
					case "xlsx":
						XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
						errors = xlsxValidator.validate(workbook);
						break;
				}

				if (errors.size() != 0){
					invalidFiles.add(new ErrorInfo(filename, errors));
				}
				else{
					validFiles.add(file.getOriginalFilename());
					storageService.store(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("valid", validFiles );
		model.addAttribute("invalid", invalidFiles );
		model.addAttribute("content", "../fragments/" + Constants.FRAGMENT_UPLOAD_SUMMARY);
		return Constants.TEMPLATE_LOGGED_IN;
	}

	/***
	 * Temporary until proper validation is implemented
	 */
	public static class ErrorInfo
	{
		private final String filename;
		private final List<ValidationError> errors;

		public ErrorInfo(String filename, List<ValidationError> errors)
		{
			this.filename = filename;
			this.errors = errors;
		}

		public String getFilename()   { return this.filename; }
		public List<ValidationError> getErrors() { return this.errors; }
	}
}
