package com.example.Dawlati.repositories;

import com.example.Dawlati.models.Form;
import com.example.Dawlati.models.FormSubmission;
import com.example.Dawlati.models.Status;
import com.example.Dawlati.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Integer> {


    List<FormSubmission> findByUserAndForm(User user, Form form);

    Optional<FormSubmission> findByIdAndStatus(Integer id, Status status);

    List<FormSubmission> findByStatusAndForm(Status status, Form form);
}
