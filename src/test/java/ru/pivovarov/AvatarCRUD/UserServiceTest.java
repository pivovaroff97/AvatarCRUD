package ru.pivovarov.AvatarCRUD;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pivovarov.AvatarCRUD.dao.UserRepository;
import ru.pivovarov.AvatarCRUD.entity.User;
import ru.pivovarov.AvatarCRUD.handler.UserStatusData;
import ru.pivovarov.AvatarCRUD.service.UserService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    List<User> users;

    @BeforeAll
    public void initUsers() {
        User user = new User();
        user.setId(1);
        user.setName("onlytest");
        user.setEmail("onlytest@mail.ru");
        user.setStatus("online");
        User user2 = new User();
        user2.setId(2);
        user2.setName("onlytest2");
        user2.setEmail("onlytest2@mail.ru");
        user2.setStatus("offline");
        users = new ArrayList<>(Arrays.asList(user, user2));
    }

    @Test
    public void getUsersTest() {
        Mockito.doReturn(users).when(userRepository).findAll();
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    public void getUsersByStatus() {
        Mockito.doReturn(new ArrayList<>(Collections.singletonList(users.get(0))))
                .when(userRepository).getAllUsersByStatus(User.Status.ONLINE);
        assertEquals(1, userService.getAllUsersByStatus(User.Status.ONLINE).size());
    }

    @Test
    public void getUserByIdTest() {
        User user = users.get(0);
        Optional<User> optional = Optional.of(user);
        Mockito.doReturn(optional).when(userRepository).findById(1);
        assertEquals("onlytest", userService.getUserById(1).getName());
    }

    @Test
    public void saveUserTest() {
        User user = users.get(0);
        assertEquals(user.getId(), userService.saveUser(user));
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void changeStatusTest() {
        User user = users.get(0);
        Optional<User> optional = Optional.of(user);
        Mockito.doReturn(optional).when(userRepository).findById(1);
        ResponseEntity<Object> responseEntity = userService.changeStatus(user.getId(), "offline");
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        System.out.println("responseEntity: " + responseEntity);
        Map<String, Object> dataMap = (Map<String, Object>) responseEntity.getBody();
        UserStatusData userStatusData = (UserStatusData) dataMap.get("data");
        assertEquals(User.Status.OFFLINE, userStatusData.getStatus());
        assertEquals(User.Status.ONLINE, userStatusData.getPreviousStatus());
    }
}
