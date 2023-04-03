package ru.pivovarov.AvatarCRUD.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pivovarov.AvatarCRUD.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
