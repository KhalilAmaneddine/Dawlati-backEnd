package com.example.Dawlati.Controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.example.Dawlati.controllers.UserController;
import com.example.Dawlati.models.*;
import com.example.Dawlati.services.AuthService;
import com.example.Dawlati.services.FormSubmissionService;
import com.example.Dawlati.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @MockBean
    private FormSubmissionService formSubmissionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void registerShouldReturn201() throws Exception {
        Role role = new Role(2, "ROLE_USER");
        User user = new User(1, "John", "Doe",
                "jakedoe@gmail.com", "password", role);
        String json = this.objectMapper.writeValueAsString(user);
        this.mvc.perform(post("http://localhost:8090/api/v1/user/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isCreated());
        verify(userService).register(user);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void loginShouldReturn200() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        when(authService.createLoginInfo(authentication)).thenReturn("jwt");
        this.mvc.perform(post("http://localhost:8090/api/v1/user/login")
                .with(csrf())
        )
                .andExpect(content().string(containsString("jwt")))
                .andExpect(status().isOk());
        verify(authService).createLoginInfo(authentication);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void getUsersShouldReturn200() throws Exception {
        UserDTO user1 = new UserDTO(1, "johndoe@gmail.com", "John", "Doe");
        UserDTO user2 = new UserDTO(2, "jakedoe@gmail.com", "Jake", "Doe");
        List<UserDTO> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userService.getUsers()).thenReturn(users);
        this.mvc.perform(get("http://localhost:8090/api/v1/user/admin/getUsers")
                        .with(csrf())
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jake"))
                .andExpect(status().isOk());
        verify(userService).getUsers();
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void getDataShouldReturn200() throws Exception {
        List<FormSubmission> formSubmissions = new ArrayList<>();
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.PENDING,
                LocalDate.now(), form, null);
        formSubmissions.add(formSubmission);
        when(formSubmissionService.getData(1)).thenReturn(formSubmissions);
        this.mvc.perform(get("http://localhost:8090/api/v1/user/admin/getData/{id}", 1)
                        .with(csrf())
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].formData").value("data"))
                .andExpect(status().isOk());
        verify(formSubmissionService).getData(1);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void adminApproveShouldReturn200() throws Exception {
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.APPROVED,
                LocalDate.now(), form, null);
        String json = this.objectMapper.writeValueAsString(formSubmission);
        when(formSubmissionService.approveForm(formSubmission)).thenReturn(formSubmission);
        this.mvc.perform(post("http://localhost:8090/api/v1/user/admin/approve", 1)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(content().string(containsString("Form approved")))
                .andExpect(status().isOk());
        verify(formSubmissionService).approveForm(formSubmission);
    }
}
