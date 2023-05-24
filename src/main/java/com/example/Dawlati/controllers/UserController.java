package com.example.Dawlati.controllers;

import com.example.Dawlati.models.*;
import com.example.Dawlati.services.AuditService;
import com.example.Dawlati.services.AuthService;
import com.example.Dawlati.services.FormSubmissionService;
import com.example.Dawlati.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {


    private final UserService userService;
    private final AuthService authService;
    private final FormSubmissionService formSubmissionService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setRole(new Role(2,"ROLE_USER"));
        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> getLoginInfo(Authentication authentication) {
        return ResponseEntity.ok(authService.createLoginInfo(authentication));
    }

    @GetMapping("/admin/getUsers")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }


    @GetMapping("/admin/getData/{id}")
    public ResponseEntity<List<FormSubmission>> getData(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.formSubmissionService.getData(id));
    }

    @PostMapping("/admin/approve")
    public ResponseEntity<String> approveForm(@RequestBody FormSubmission formSubmission) {
        formSubmission.setStatus(Status.APPROVED);
        this.formSubmissionService.approveForm(formSubmission);
        return ResponseEntity.ok("Form approved");
    }
}
