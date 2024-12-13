package com.example.finle.service;

import com.example.finle.entity.Photo;
import com.example.finle.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";


    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }


    public Photo uploadPhoto(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);


        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }


        Files.write(filePath, file.getBytes());


        Photo photo = new Photo();
        photo.setImageLink("/images/" + fileName);
        photo.setImageName(fileName);
        return photoRepository.save(photo);
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public void deletePhoto(Long photoId) throws IOException {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found"));


        Path filePath = Paths.get(UPLOAD_DIR + photo.getImageLink().replace("/images/", ""));
        Files.deleteIfExists(filePath);


        photoRepository.delete(photo);
    }

    public List<Photo> searchPhoto(String title) {
        return photoRepository.findByTitleContaining(title);
    }
}
