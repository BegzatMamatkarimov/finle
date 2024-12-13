package com.example.finle.controller;


import com.example.finle.entity.Photo;
import com.example.finle.service.PhotoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/")
    public String getAllPhotos(Model model) {
        List<Photo> photos = photoService.getAllPhotos(); // Получаем все фотографии из БД
        model.addAttribute("photos", photos); // Передаем их в модель
        return "index"; // Название шаблона (index.html)
    }
    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            photoService.uploadPhoto(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deletePhoto(@PathVariable Long id) {
        try {
            photoService.deletePhoto(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchPhotos(@RequestParam String query, Model model) {
        List<Photo> photos = photoService.searchPhoto(query);
        model.addAttribute("photos", photos);
        model.addAttribute("query", query);
        return "index";
    }
}
