package ru.pivovarov.AvatarCRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pivovarov.AvatarCRUD.entity.User;
import ru.pivovarov.AvatarCRUD.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> getUsers(@RequestParam(value = "status", required = false) String status) {
        try {
            if (status != null) {
                return userService.getAllUsersByStatus(User.Status.valueOf(status.toUpperCase()));
            }
            return userService.getAllUsers();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/")
    public int saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> changeStatus(@PathVariable("id") int id, @RequestParam String status) {
        return userService.changeStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
