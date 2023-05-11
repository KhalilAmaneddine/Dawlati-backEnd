package com.example.Dawlati.Services;

import com.example.Dawlati.Models.User;
import com.example.Dawlati.Models.UserDTO;
import com.example.Dawlati.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User was not found!"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public List<UserDTO> getUsers() {
        List<UserDTO> users = new ArrayList<>();
        List<User> newUsers = userRepository.findAll();
        for(int i = 0; i < newUsers.size(); i++) {
            users.add(new UserDTO(newUsers.get(i).getId(), newUsers.get(i).getEmail(),
                    newUsers.get(i).getFirstname(), newUsers.get(i).getLastname())) ;
        }
        return users;
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    /*public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        auditService.deleteByUser(user);
        formSubmissionService.deleteByUser(user);
        userRepository.deleteById(user.getId());
    }*/
}
