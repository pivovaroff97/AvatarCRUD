package ru.pivovarov.AvatarCRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.pivovarov.AvatarCRUD.dao.UserRepository;
import ru.pivovarov.AvatarCRUD.entity.User;
import ru.pivovarov.AvatarCRUD.handler.ResponseHandler;
import ru.pivovarov.AvatarCRUD.handler.UserStatusData;

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

    @Override
    public List<User> getAllUsersByStatus(User.Status status) {
        return userRepository.getAllUsersByStatus(status);
    }

    @Override
    public ResponseEntity<Object> changeStatus(int id, String status) {
        try {
            User us = getUserById(id);
            User.Status previousStatus = us.getStatus();
            us.setStatus(status);
            saveUser(us);
            UserStatusData statusData = new UserStatusData();
            statusData.setId(us.getId());
            statusData.setStatus(us.getStatus());
            statusData.setPreviousStatus(previousStatus);
            return ResponseHandler.generateResponse("status is changed", HttpStatus.OK, statusData);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }
}
