package com.example.Dawlati.controllers;

import com.example.Dawlati.models.*;
import com.example.Dawlati.services.FormSubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/formsubmission")
@AllArgsConstructor
public class FormSubmissionController {

    private final FormSubmissionService formSubmissionService;

    @PostMapping("/save")
    public ResponseEntity<FormSubmission> saveForm(@RequestBody FormSubmission formSubmission,
                                                   Authentication authentication) {
        return new ResponseEntity<>(this.formSubmissionService.saveForm(formSubmission, authentication),
                HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<FormSubmission> submitForm(@RequestBody FormSubmission formSubmission,
                                                     Authentication authentication) {
        return new ResponseEntity<>(this.formSubmissionService.submitForm(formSubmission, authentication),
                HttpStatus.CREATED);
    }
   @GetMapping("/getSavedData/{id}")
   public ResponseEntity<String> getSavedData(@PathVariable("id") Integer id,
                                              Authentication authentication) {
       return ResponseEntity.ok(formSubmissionService.getSavedExtract(id, authentication));
   }


    @DeleteMapping("/deleteExtract/{id}")
    public void deleteExtract(@PathVariable("id") Integer id) {
        formSubmissionService.deleteExtract(id);
    }


    @PostMapping("/formPrinted")
    public void auditPrint(@RequestBody String formName) {
        formSubmissionService.printForm();
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<FormSubmission>> getHistory(@PathVariable("id") Integer id,
                                                   Authentication authentication) {
        return ResponseEntity.ok(formSubmissionService.getHistory(id, authentication));
    }



}
