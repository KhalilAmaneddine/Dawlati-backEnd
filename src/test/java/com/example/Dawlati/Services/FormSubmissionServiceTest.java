package com.example.Dawlati.Services;

import com.example.Dawlati.models.Form;
import com.example.Dawlati.models.FormSubmission;
import com.example.Dawlati.models.Status;
import com.example.Dawlati.models.User;
import com.example.Dawlati.repositories.FormSubmissionRepository;
import com.example.Dawlati.services.FormService;
import com.example.Dawlati.services.FormSubmissionService;
import com.example.Dawlati.services.NotificationService;
import com.example.Dawlati.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FormSubmissionServiceTest {

    @Mock private FormSubmissionRepository formSubmissionRepository;
    @InjectMocks
    private FormSubmissionService formSubmissionService;

    @Mock private UserService userService;

    @Mock private NotificationService notificationService;
    @Mock private FormService formService;
    @Test
    void getSavedExtractShouldInvokeFindByIdAndStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission1 = new FormSubmission("data", Status.PENDING, LocalDate.now(),
                form, user);
        formSubmission1.setId(1);
        when(formSubmissionRepository.findByIdAndStatus(1, Status.PENDING))
                .thenReturn(Optional.of(formSubmission1));
        String expected = formSubmissionService.getSavedExtract(1, authentication);
        Assertions.assertEquals("data", expected);
        verify(formSubmissionRepository).findByIdAndStatus(1, Status.PENDING);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void saveFormShouldInvokeSave() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("johndoe@gmail.com", "password");
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.PENDING, LocalDate.now(),
                form, user);
        when(formSubmissionRepository.save(formSubmission))
                .thenReturn(formSubmission);
        FormSubmission expected = formSubmissionService.saveForm(formSubmission, authentication);
        Assertions.assertEquals(expected, formSubmission);
        verify(formSubmissionRepository).save(formSubmission);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void approveFormShouldInvokeSave() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission(1, "data", Status.PENDING, LocalDate.now(),
                form, user);
        Integer id = formSubmission.getId();
        when(formSubmissionRepository.findById(id))
                .thenReturn(Optional.of(formSubmission));
        when(formSubmissionRepository.save(formSubmission))
                .thenReturn(formSubmission);
        FormSubmission expected = formSubmissionService.approveForm(formSubmission);
        Assertions.assertEquals(expected, formSubmission);
        verify(formSubmissionRepository).save(formSubmission);
    }

    @Test
    void getHistoryShouldReturnListOfFormSubmissions() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("johndoe@gmail.com", "password");
        User user = new User(1,"John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission(1, "data", Status.PENDING,
                LocalDate.now(), form, user);
        List<FormSubmission> formSubmissions = new ArrayList<>();
        formSubmissions.add(formSubmission);
        Integer id = form.getId();
        when(userService.findByEmail(user.getEmail()))
                .thenReturn(user);
        when(formService.findById(id))
                .thenReturn(form);
        when(formSubmissionRepository.findByUserAndForm(user, form))
                .thenReturn(formSubmissions);
        List<FormSubmission> expected = formSubmissionService.getHistory(id, authentication);
        for(int i = 0; i < formSubmissions.size(); i++) {
            Assertions.assertEquals(expected.get(i), formSubmissions.get(i));
        }
        verify(formSubmissionRepository).findByUserAndForm(user, form);
    }

    @Test
    void getDataShouldReturnListOfFormSubmissions() {
        User user = new User(1,"John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission1 = new FormSubmission(1, "data", Status.SUBMITTED,
                LocalDate.now(), form, user);
        FormSubmission formSubmission2 = new FormSubmission(2, "data", Status.SUBMITTED,
                LocalDate.now(), form, user);
        List<FormSubmission> formSubmissions = new ArrayList<>();
        formSubmissions.add(formSubmission1);
        formSubmissions.add(formSubmission2);
        Integer id = form.getId();
        when(formService.findById(id))
                .thenReturn(form);
        when(formSubmissionService.getData(id)).thenReturn(formSubmissions);
        List<FormSubmission> expected = formSubmissionService.getData(id);
        for(FormSubmission formSubmission: formSubmissions) {
            Assertions.assertEquals(formSubmission.getStatus(), Status.SUBMITTED);
        }
        verify(formSubmissionRepository).findByStatusAndForm(Status.SUBMITTED, form);
    }

    @Test
    @WithMockUser(username = "johndoe@gmail.com", password = "password")
    void submitFormShouldInvokeSave() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("johndoe@gmail.com", "password");
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission = new FormSubmission("data", Status.SUBMITTED, LocalDate.now(),
                form, user);
        when(formSubmissionRepository.save(formSubmission))
                .thenReturn(formSubmission);
        FormSubmission expected = formSubmissionService.submitForm(formSubmission, authentication);
        Assertions.assertEquals(expected, formSubmission);
        verify(formSubmissionRepository).save(formSubmission);
    }


}
