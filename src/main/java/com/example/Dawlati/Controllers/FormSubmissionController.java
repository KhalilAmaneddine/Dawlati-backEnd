package com.example.Dawlati.Controllers;

import com.example.Dawlati.Models.*;
import com.example.Dawlati.Services.AuditService;
import com.example.Dawlati.Services.FormService;
import com.example.Dawlati.Services.FormSubmissionService;
import com.example.Dawlati.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/formsubmission")
@AllArgsConstructor
public class FormSubmissionController {

    private final FormSubmissionService formSubmissionService;
    private final AuditService auditService;
    private final UserService userService;
    private final FormService formService;
    @PostMapping("/save/{id}")
    public ResponseEntity<FormSubmission> saveForm(@RequestBody FormSubmission formSubmission,
                                                            @PathVariable("id") Integer id,
                                                            Authentication authentication) {
        Form form = formService.findById(id);
        User user = userService.findByEmail(authentication.getName());
        formSubmission.setDate(LocalDate.now());
        formSubmission.setUser(user);
        formSubmission.setForm(form);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " saved a " + form.getFormName() + " extract draft", "Save", user);
        auditService.add(auditLog);
        try {
            FormSubmission formSubmission1 = formSubmissionService.getSavedExtract(user,
                    formSubmission.getForm(), Status.PENDING);
            formSubmission.setId(formSubmission1.getId());
        } catch (Exception e) {

        }

        return new ResponseEntity<>(formSubmissionService.saveForm(formSubmission), HttpStatus.CREATED);
    }


    @PostMapping("/submit/{id}")
    public ResponseEntity<FormSubmission> submitForm(@RequestBody FormSubmission formSubmission,
            @PathVariable("id") Integer id,
            Authentication authentication) {
        Form form = formService.findById(id);
        User user = userService.findByEmail(authentication.getName());
        formSubmission.setDate(LocalDate.now());
        formSubmission.setUser(user);
        formSubmission.setForm(form);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " submitted a " + form.getFormName() + " extract draft",
                "Submit", user);
        auditService.add(auditLog);
        try {
            FormSubmission formSubmission1 = formSubmissionService.getSavedExtract(user, formSubmission.getForm()
                    , Status.PENDING);
            formSubmission.setId(formSubmission1.getId());
        } catch(Exception e) {
        }
        return new ResponseEntity<>(formSubmissionService.saveForm(formSubmission), HttpStatus.CREATED);
    }

    @GetMapping("/getSavedData/{id}")
    public ResponseEntity<String> getSavedData(@PathVariable("id") Integer id,
            Authentication authentication) {

        User user = userService.findByEmail(authentication.getName());
        Form form = formService.findById(id);
        try {
            FormSubmission formSubmission = formSubmissionService.getSavedExtract(user, form, Status.PENDING);
            return ResponseEntity.ok(formSubmission.getFormData());
        } catch(Exception e) {
            return ResponseEntity.ok("No data available");
        }
    }

    @DeleteMapping("/deleteExtract/{id}")
    public void deleteExtract(@PathVariable("id") Integer id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Form form = formService.findById(id);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),
                "User " + authentication.getName()
                        + " deleted a " + form.getFormName() +" extract draft", "Delete", user);
        auditService.add(auditLog);
        try {
            FormSubmission formSubmission = formSubmissionService.getSavedExtract(user, form,  Status.PENDING);
            formSubmissionService.deleteExtract(formSubmission);
        } catch(Exception e) {

        }
    }

    @PostMapping("/formPrinted")
    public void auditPrint(@RequestBody String formName,
                           Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),
                "User " + authentication.getName() + " printed a " + formName + " Extract",
                "Print", user);
        auditService.add(auditLog);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<String>> getHistory(@PathVariable("id") Integer id,
            Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Form form = formService.findById(id);
        AuditLog auditLog = new AuditLog(LocalDateTime.now(),
                "User " + authentication.getName() + " viewed his " + form.getFormName()
                        +" Extract History", "View", user);
        auditService.add(auditLog);
        List<FormSubmission> formSubmissions = formSubmissionService.getHistory(user, form);
        //List<String> formData = new ArrayList<>();
        List<String> formSubmissionData = new ArrayList<>();
        for(int i = 0; i < formSubmissions.size(); i++) {
            if(formSubmissions.get(i).getStatus() == Status.SUBMITTED)
                //formData.add(formSubmissions.get(i).getFormData());
                formSubmissionData.add(formSubmissions.get(i).getFormData());
        }

        return ResponseEntity.ok(formSubmissionData);
    }
}
