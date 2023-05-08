package com.example.Dawlati.Services;

import com.example.Dawlati.Models.Form;
import com.example.Dawlati.Models.FormSubmission;
import com.example.Dawlati.Models.Status;
import com.example.Dawlati.Models.User;
import com.example.Dawlati.Repositories.FormSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormSubmissionService {
    private final FormSubmissionRepository formSubmissionRepository;
    public FormSubmission getSavedExtract(User user, Form form, Status status) {
        return formSubmissionRepository.findByUserAndFormAndStatus(user,form, status)
                .orElseThrow( () -> new IllegalStateException("No saved data"));
    }

    public FormSubmission saveForm(FormSubmission formSubmission) {
        return formSubmissionRepository.save(formSubmission);
    }

    public void deleteExtract(FormSubmission formSubmission) {
        formSubmissionRepository.deleteById(formSubmission.getId());
    }

    public List<FormSubmission> getHistory(User user, Form form) {
        return formSubmissionRepository.findByUserAndForm(user, form);
    }

    public void deleteByUser(User user) {
        formSubmissionRepository.deleteByUser(user);
    }
}
