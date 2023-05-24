package com.example.Dawlati.services;

import com.example.Dawlati.annotations.Audit;
import com.example.Dawlati.exceptions.UserEmailNotFoundException;
import com.example.Dawlati.models.AuditLog;
import com.example.Dawlati.models.User;
import com.example.Dawlati.models.UserDTO;
import com.example.Dawlati.models.AuditLogAction;
import com.example.Dawlati.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;


    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepository.save(user);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User registered: " +
                user.getUsername(), AuditLogAction.Register, user);
        auditService.add(auditLog);
        return registeredUser;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserEmailNotFoundException("User with email: " + email +
                        " was not found"));
    }


    public List<UserDTO> getUsers() {
        List<UserDTO> users = new ArrayList<>();
        List<User> newUsers = userRepository.findAll();
        for (User user : newUsers) {
            users.add(new UserDTO(user.getId(),user.getEmail(), user.getFirstname(), user.getLastname()));
        }
        return users;
    }


}
