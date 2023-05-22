package ru.pivovarov.AvatarCRUD.service;

import ru.pivovarov.AvatarCRUD.entity.Picture;

import java.util.List;

public interface PictureService {
    public List<Picture> getAllPictures();
    public Picture getPictureById(int id);
    public int savePicture(Picture picture);
    public void deletePictureById(int id);
}
