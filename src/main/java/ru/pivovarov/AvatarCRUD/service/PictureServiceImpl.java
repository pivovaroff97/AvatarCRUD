package ru.pivovarov.AvatarCRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pivovarov.AvatarCRUD.dao.PictureRepository;
import ru.pivovarov.AvatarCRUD.entity.Picture;

import java.util.List;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;
    @Override
    public List<Picture> getAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Picture getPictureById(int id) {
        Picture picture = null;
        Optional<Picture> pictureOptional = pictureRepository.findById(id);
        if (pictureOptional.isPresent()) {
            picture = pictureOptional.get();
        }
        return picture;
    }

    @Override
    public int savePicture(Picture picture) {
        return pictureRepository.save(picture).getId();
    }

    @Override
    public void deletePictureById(int id) {
        pictureRepository.deleteById(id);
    }
}
