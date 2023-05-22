package ru.pivovarov.AvatarCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pivovarov.AvatarCRUD.entity.Picture;
import ru.pivovarov.AvatarCRUD.handler.ResponseHandler;
import ru.pivovarov.AvatarCRUD.service.PictureService;
import ru.pivovarov.AvatarCRUD.service.StorageService;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
public class PictureRestController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/")
    public Picture savePicture(@RequestBody MultipartFile multipartFile) {
        String fileKey = storageService.store(multipartFile);
        Picture picture = new Picture();
        picture.setName(multipartFile.getOriginalFilename());
        picture.setPictureKey(fileKey);
        pictureService.savePicture(picture);
        return picture;
    }

    @GetMapping("/")
    public List<Picture> getAllPictures() {
        return pictureService.getAllPictures();
    }

    @DeleteMapping("/{id}")
    public void deletePictureById(@PathVariable("id") int id) {
        Picture picture = pictureService.getPictureById(id);
        if (picture != null) {
            pictureService.deletePictureById(id);
            storageService.deleteByFileKey(picture.getPictureKey());
        }
    }

    @DeleteMapping("/delete-files")
    public ResponseEntity<Object> deleteAllFiles() {
        try {
            storageService.deleteAll();
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
        return ResponseHandler.generateResponse("files has been deleted", HttpStatus.OK, null);
    }
}
