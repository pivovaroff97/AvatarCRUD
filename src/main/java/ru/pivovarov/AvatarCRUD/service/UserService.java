package ru.pivovarov.AvatarCRUD.service;

import ru.pivovarov.AvatarCRUD.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public int saveUser(User user);
    public void deleteUserById(int id);
}
