package es.oaw.irapvalidator.controller;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.model.ResponseValidatedFile;
import es.oaw.irapvalidator.model.ValidatedFile;
import es.oaw.irapvalidator.storage.FileDbStorageService;
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

import java.io.IOException;
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
		boolean valid = true;
		List<String> validFiles = new ArrayList<>();
		List<ErrorInfo> invalidFiles = new ArrayList<>();

		for (MultipartFile file : fileMap.values()) {
			try {
				ErrorInfo errors = validate(file, valid);
				if (errors.getErrors().size() != 0){
					invalidFiles.add(errors);
				}
				else{
					validFiles.add(file.getOriginalFilename());
					storageService.store(file);
				}

				valid = !valid;
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
		private final List<String> errors;

		public ErrorInfo(String filename, List<String> errors)
		{
			this.filename = filename;
			this.errors = errors;
		}

		public String getFilename()   { return this.filename; }
		public List<String> getErrors() { return this.errors; }
		public void addError(String error){this.errors.add(error); }
	}

	/***
	 * Temporary until proper validation is implemented
	 */
	private ErrorInfo validate(MultipartFile file, boolean valid){
		ErrorInfo errors = new ErrorInfo(file.getOriginalFilename(), new ArrayList<>());
		if (!valid){
			errors.addError("Error1");
			errors.addError("Error2");
		}
		return errors;
	}

}
