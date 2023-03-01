package fa.training.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UploadService {
    public List<Map<String, String>> upload(MultipartFile file) throws Exception;
}
