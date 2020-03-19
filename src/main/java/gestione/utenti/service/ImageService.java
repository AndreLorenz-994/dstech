package gestione.utenti.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import gestione.utenti.model.Image;
import gestione.utenti.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                return null;
            }

            Image image = new Image(fileName, file.getContentType(), file.getBytes());

            return imageRepository.save(image);
        } catch (IOException ex) {
            return null;
        }
    }

    public Image getFile(String fileId) {
        return imageRepository.findById(fileId).orElseThrow(() -> null);
    }
}
