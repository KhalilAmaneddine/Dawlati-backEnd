package com.example.Dawlati.Controllers;

import com.example.Dawlati.Models.*;
import com.example.Dawlati.Services.AuditService;
import com.example.Dawlati.Services.AuthService;
import com.example.Dawlati.Services.FormSubmissionService;
import com.example.Dawlati.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {


    private final UserService userService;
    private final AuthService authService;
    private final AuditService auditService;
    private final FormSubmissionService formSubmissionService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setRole(new Role(2,"ROLE_USER"));
        User registeredUser = userService.register(user);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User registered: " +
                registeredUser.getUsername(), "REGISTER", registeredUser);
        auditService.add(auditLog);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> getLoginInfo(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),"User Login: " + authentication.getName(),
                "Login", user);
        auditService.add(auditLog);

        return ResponseEntity.ok(authService.createLoginInfo(authentication));
    }

    @GetMapping("/admin/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Transactional
    @DeleteMapping("/admin/deleteUser/{email}")
    public void deleteUser(@PathVariable("email") String email) {
        User user = userService.findByEmail(email);
        auditService.deleteByUser(user);
        formSubmissionService.deleteByUser(user);
        userService.deleteUser(user.getId());
    }

    /*@Transactional
    @DeleteMapping("/admin/deleteUser/{email}")
    public void deleteUser(@PathVariable("email") String email) {
        userService.deleteUser(email);
    }*/
    @GetMapping("/admin/getData")
    public ResponseEntity<List<FormSubmission>> getData() {
        return ResponseEntity.ok(this.formSubmissionService.getData());
    }

    @PostMapping("/admin/approve")
    public ResponseEntity<String> approveForm(@RequestBody FormSubmission formSubmission) {
        formSubmission.setStatus(Status.APPROVED);
        this.formSubmissionService.approveForm(formSubmission);
        return ResponseEntity.ok("Form approved");
    }
}
