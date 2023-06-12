package com.example.Dawlati.Repositories;


import com.example.Dawlati.exceptions.UserEmailNotFoundException;
import com.example.Dawlati.models.User;
import com.example.Dawlati.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    void findByEmailShouldReturnAStudent() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        userRepository.save(user);
        User expectedUser = userRepository.findByEmail("johndoe@gmail.com")
                .orElseThrow(()->new UserEmailNotFoundException("user not found"));
        assertEquals("johndoe@gmail.com", expectedUser.getEmail());
        //Assertions.assertEquals(expectedUser, user);
    }
}
