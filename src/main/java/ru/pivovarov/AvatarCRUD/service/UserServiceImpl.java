package ru.pivovarov.AvatarCRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pivovarov.AvatarCRUD.dao.UserRepository;
import ru.pivovarov.AvatarCRUD.entity.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public int saveUser(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
}
