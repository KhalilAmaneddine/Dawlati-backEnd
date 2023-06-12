package com.example.Dawlati.Repositories;

import com.example.Dawlati.models.Form;
import com.example.Dawlati.models.FormSubmission;
import com.example.Dawlati.models.Status;
import com.example.Dawlati.models.User;
import com.example.Dawlati.repositories.FormRepository;
import com.example.Dawlati.repositories.FormSubmissionRepository;
import com.example.Dawlati.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import java.util.List;

@DataJpaTest
public class FormSubmissionRepositoryTest {
    @Autowired
    private FormSubmissionRepository formSubmissionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormRepository formRepository;
    @Test
    void FindByUserAndFormShouldReturnAListOfFormSubmissions() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission1 = new FormSubmission("data", Status.PENDING, LocalDate.now(),
                form, user);
        FormSubmission formSubmission2 = new FormSubmission("data", Status.SUBMITTED, LocalDate.now(),
                form, user);
        userRepository.save(user);
        formRepository.save(form);
        formSubmissionRepository.save(formSubmission1);
        formSubmissionRepository.save(formSubmission2);
        List<FormSubmission> formSubmissions = formSubmissionRepository.findByUserAndForm(user, form);
        Assertions.assertEquals(2, formSubmissions.size());

        for(FormSubmission formSubmission: formSubmissions) {
            Assertions.assertEquals(user, formSubmission.getUser());
            Assertions.assertEquals(form, formSubmission.getForm());
        }
    }

    @Test
    void FindByStatusAndFormShouldReturnListOfFormSubmissions() {
        User user = new User("John", "Doe", "johndoe@gmail.com", "password" );
        Form form = new Form(1, "Civil Extract");
        FormSubmission formSubmission1 = new FormSubmission("data", Status.PENDING, LocalDate.now(),
                form, user);
        FormSubmission formSubmission2 = new FormSubmission("data", Status.PENDING, LocalDate.now(),
                form, user);
        userRepository.save(user);
        formRepository.save(form);
        formSubmissionRepository.save(formSubmission1);
        formSubmissionRepository.save(formSubmission2);
        List<FormSubmission> formSubmissions = formSubmissionRepository.findByStatusAndForm(Status.PENDING,
                form);
        Assertions.assertEquals(2, formSubmissions.size());

        for(FormSubmission formSubmission: formSubmissions) {
            Assertions.assertEquals(user, formSubmission.getUser());
            Assertions.assertEquals(form, formSubmission.getForm());
        }
    }


}
