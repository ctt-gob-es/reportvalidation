package es.oaw.irapvalidator.controller;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.storage.StorageFileNotFoundException;
import es.oaw.irapvalidator.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = Constants.UPLOAD_CONTROLLER_PATH)
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
		List<String> files = storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList());
		model.addAttribute("files", files);

		model.addAttribute("content", "../fragments/" + Constants.FRAGMENT_UPLOAD_FORM);
		return Constants.TEMPLATE_LOGGED_IN;
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("files") MultipartFile[] files,
			RedirectAttributes redirectAttributes, Model model) {
		boolean valid = true;
		boolean empty = Arrays.stream(files).allMatch(MultipartFile::isEmpty);
		List<String> validFiles = new ArrayList<>();
		List<ErrorInfo> invalidFiles = new ArrayList<>();

		if (!empty){
			for (MultipartFile file : files){
				ErrorInfo errors = validate(file, valid);
				if (errors.getErrors().size() != 0){
					invalidFiles.add(errors);
				}
				else{
					validFiles.add(file.getOriginalFilename());
					storageService.store(file);
				}

				valid = !valid;
			}
		}

		model.addAttribute("valid", validFiles );
		model.addAttribute("invalid", invalidFiles );
		model.addAttribute("content", "../fragments/" + Constants.FRAGMENT_UPLOAD_SUMMARY);
		return Constants.TEMPLATE_LOGGED_IN;	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
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
