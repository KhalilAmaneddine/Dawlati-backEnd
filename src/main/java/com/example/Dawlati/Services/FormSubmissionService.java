package com.example.Dawlati.Services;

import com.example.Dawlati.Models.*;
import com.example.Dawlati.Repositories.FormSubmissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FormSubmissionService {
    private final FormSubmissionRepository formSubmissionRepository;
    private final NotificationService notificationService;
    private final FormService formService;
    private final UserService userService;
    private final AuditService auditService;


    public String getSavedExtract(Integer id, Authentication authentication) {
        try {
            FormSubmission formSubmission =
                    formSubmissionRepository.findByIdAndStatus(id, Status.PENDING)
                            .orElseThrow(() -> new IllegalStateException("No data found"));
            return formSubmission.getFormData();
        } catch(Exception e) {
            return "No data available";
        }
    }

    public FormSubmission saveForm(FormSubmission formSubmission, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        formSubmission.setDate(LocalDate.now());
        formSubmission.setUser(user);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " saved a " + formSubmission.getForm().getFormName() + " extract draft", "Save", user);
        auditService.add(auditLog);
        return formSubmissionRepository.save(formSubmission);
    }

    public FormSubmission approveForm(FormSubmission formSubmission) {
        FormSubmission formSubmission1 = this.formSubmissionRepository.findById(formSubmission.getId())
                .orElseThrow(() -> new IllegalStateException("No form found"));
        User user = formSubmission1.getUser();
        Notification notification = new Notification(NotificationType.EMAIL, "Your form has been approved", "Approval",
                LocalDateTime.now(),
                0, 0, user);
        notificationService.save(notification);
        formSubmission.setUser(user);
        return formSubmissionRepository.save(formSubmission);
    }


    public void deleteExtract(Integer id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Form form = formService.findById(id);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),
                "User " + authentication.getName()
                        + " deleted a " + form.getFormName() +" extract draft", "Delete", user);
        auditService.add(auditLog);

        try {
            FormSubmission formSubmission =
                    formSubmissionRepository.findByUserAndFormAndStatus(user,form, Status.PENDING)
                    .orElseThrow( () -> new IllegalStateException("No saved data"));
                    formSubmissionRepository.deleteById(formSubmission.getId());
        } catch(Exception e) {

        }
    }

    public List<FormSubmission> getHistory(Integer id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Form form = formService.findById(id);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),
                "User " + authentication.getName() + " viewed his " + form.getFormName()
                        +" Extract History", "View", user);
        auditService.add(auditLog);
        List<FormSubmission> formSubmissions = formSubmissionRepository.findByUserAndForm(user, form);
        return formSubmissions;
    }

    public List<FormSubmission> getData(Integer id) {
        Form form = formService.findById(id);
        return this.formSubmissionRepository.findByStatusAndForgitm(Status.SUBMITTED, form);
    }

    public FormSubmission submitForm(FormSubmission formSubmission,
                                     Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        formSubmission.setDate(LocalDate.now());
        formSubmission.setUser(user);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " submitted a " + formSubmission.getForm().getFormName() + " extract draft",
                "Submit", user);
        auditService.add(auditLog);
        Notification notification = new Notification( NotificationType.EMAIL,"User submitted a form",
                "SUBMITTAL",
                LocalDateTime.now(),
                0, 0, formSubmission.getUser());
        notificationService.save(notification);
        return formSubmissionRepository.save(formSubmission);
    }

    public void printForm(String formName, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),
                "User " + authentication.getName() + " printed a " + formName + " Extract",
                "Print", user);
        auditService.add(auditLog);
    }
}
