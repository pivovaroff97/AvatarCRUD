package ru.pivovarov.AvatarCRUD.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pivovarov.AvatarCRUD.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> getAllUsersByStatus(User.Status status);
}
