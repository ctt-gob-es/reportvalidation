package es.oaw.irapvalidator.storage;

import java.io.IOException;
import java.util.stream.Stream;

import es.oaw.irapvalidator.model.ValidatedFile;
import es.oaw.irapvalidator.repository.ValidatedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileDbStorageService {

    @Autowired
    private ValidatedFileRepository fileRepository;

    public ValidatedFile store(MultipartFile file) throws IOException {
        try{
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            ValidatedFile FileDB = new ValidatedFile(fileName, file.getContentType(), file.getBytes());
            return fileRepository.save(FileDB);
        }
        catch (Exception e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public ValidatedFile getFile(String id) {
        return fileRepository.findById(id).get();
    }

    public Stream<ValidatedFile> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}