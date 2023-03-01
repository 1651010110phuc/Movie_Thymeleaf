package fa.training.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    String getStoredFileName(MultipartFile file, String id);

    void store(MultipartFile file, String storedFileName);

    Resource loadAsResource(String fileName);

    Path load(String fileName);

    void delete(String storedFilename) throws IOException;

    void init();
}
