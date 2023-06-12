package com.example.Dawlati.services;

import com.example.Dawlati.annotations.Audit;
import com.example.Dawlati.exceptions.FormSubmissionNotFoundException;
import com.example.Dawlati.models.*;
import com.example.Dawlati.repositories.FormSubmissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class FormSubmissionService {
    private final FormSubmissionRepository formSubmissionRepository;
    private final NotificationService notificationService;
    private final FormService formService;
    private final UserService userService;


    public String getSavedExtract(Integer id, Authentication authentication) {
               FormSubmission formSubmission =
                       formSubmissionRepository.findByIdAndStatus(id, Status.PENDING)
                               .orElseThrow(() -> new FormSubmissionNotFoundException("FormSubmission Not Found"));
               return formSubmission.getFormData();

    }

    @Audit(action = AuditLogAction.Save, details = "User saved his Form")
    public FormSubmission saveForm(FormSubmission formSubmission, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        formSubmission.setDate(LocalDate.now());
        formSubmission.setUser(user);
        return formSubmissionRepository.save(formSubmission);
    }

    public FormSubmission approveForm(FormSubmission formSubmission) {
        FormSubmission formSubmission1 = this.formSubmissionRepository.findById(formSubmission.getId())
                .orElseThrow(() -> new FormSubmissionNotFoundException("FormSubmission Not Found"));
        User user = formSubmission1.getUser();
        Notification notification = new Notification(NotificationType.EMAIL, "Your form has been approved", "Approval",
                LocalDateTime.now(),
                0, 0, user);
        notificationService.save(notification);
        formSubmission.setUser(user);
        return formSubmissionRepository.save(formSubmission);
    }


    @Audit(action = AuditLogAction.Delete, details = "User deleted his draft")
    public void deleteExtract(Integer id) {
        formSubmissionRepository.deleteById(id);
    }

    @Audit(action = AuditLogAction.View, details = "User Viewed History")
    public List<FormSubmission> getHistory(Integer id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Form form = formService.findById(id);
        List<FormSubmission> formSubmissions = formSubmissionRepository.findByUserAndForm(user, form);
        return formSubmissions;
    }

    public List<FormSubmission> getData(Integer id) {
        Form form = formService.findById(id);
        return this.formSubmissionRepository.findByStatusAndForm(Status.SUBMITTED, form);
    }

    @Audit(action = AuditLogAction.Submit, details = "User Submitted an extract")
    public FormSubmission submitForm(FormSubmission formSubmission,
                                     Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        formSubmission.setDate(LocalDate.now());
        formSubmission.setUser(user);
        Notification notification = new Notification( NotificationType.EMAIL,"User submitted a form",
                "SUBMITTAL",
                LocalDateTime.now(),
                0, 0, formSubmission.getUser());
        notificationService.save(notification);
        return formSubmissionRepository.save(formSubmission);
    }

    @Audit(action = AuditLogAction.Print, details = "User printed a Form")
    public void printForm() {
    }
}
