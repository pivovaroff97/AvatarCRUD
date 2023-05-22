package ru.pivovarov.AvatarCRUD.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pivovarov.AvatarCRUD.entity.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
}
