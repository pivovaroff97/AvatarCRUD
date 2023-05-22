package ru.pivovarov.AvatarCRUD.service;

import org.springframework.http.ResponseEntity;
import ru.pivovarov.AvatarCRUD.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public int saveUser(User user);
    public void deleteUserById(int id);
    public List<User> getAllUsersByStatus(User.Status status);
    public ResponseEntity<Object> changeStatus(int id, String status);
}
