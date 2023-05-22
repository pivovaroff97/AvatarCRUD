package ru.pivovarov.AvatarCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pivovarov.AvatarCRUD.entity.Picture;
import ru.pivovarov.AvatarCRUD.entity.User;
import ru.pivovarov.AvatarCRUD.exceptionHandling.StorageFileNotFoundException;
import ru.pivovarov.AvatarCRUD.service.PictureService;
import ru.pivovarov.AvatarCRUD.service.StorageService;
import ru.pivovarov.AvatarCRUD.service.UserService;

import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @GetMapping("/")
    public String uploadUsers(Model model) throws IOException {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/user-form")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(User user, @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            String fileKey = storageService.store(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
            Picture picture = new Picture();
            picture.setPictureKey(fileKey);
            picture.setName(file.getOriginalFilename());
            pictureService.savePicture(picture);
            user.setPicture(picture);
        }
        userService.saveUser(user);

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
