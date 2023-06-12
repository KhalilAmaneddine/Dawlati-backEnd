package com.example.Dawlati.Services;

import com.example.Dawlati.models.User;
import com.example.Dawlati.models.UserDTO;
import com.example.Dawlati.repositories.UserRepository;
import com.example.Dawlati.services.AuditService;
import com.example.Dawlati.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Mock private AuditService auditService;
    @Mock private PasswordEncoder passwordEncoder;

    @Test
    void registerShouldInvokeSave() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        when(userRepository.save(user))
                .thenReturn(user);
        User expected = userService.register(user);
        Assertions.assertEquals(user.getEmail() , expected.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void findByEmailShouldInvokeFindByEmail() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        String email = "johndoe@gmail.com";
        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));
        User expected = userService.findByEmail(email);
        verify(userRepository).findByEmail(email);

    }

    @Test
    void getUsersShouldInvokeFindAll() {
        User user1 = new User("John", "Doe", "johndoe@gmail.com", "password" );
        User user2 = new User("jake", "Doe", "jakedoe@gmail.com", "password" );
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll())
                .thenReturn(users);
        List<UserDTO> expected = userService.getUsers();
        verify(userRepository).findAll();

    }
}
