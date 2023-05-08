package com.example.Dawlati.Repositories;

import com.example.Dawlati.Models.Form;
import com.example.Dawlati.Models.FormSubmission;
import com.example.Dawlati.Models.Status;
import com.example.Dawlati.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Integer> {

    Optional<FormSubmission> findByUserAndFormAndStatus(User user, Form form, Status status);

    List<FormSubmission> findByUserAndForm(User user, Form form);

    void deleteByUser(User user);
}
