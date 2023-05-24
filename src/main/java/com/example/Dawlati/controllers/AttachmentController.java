package com.example.Dawlati.controllers;

import com.example.Dawlati.models.Form;
import com.example.Dawlati.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;


    @PostMapping("/civil")
    public ResponseEntity<String> uploadCivil(@RequestParam("file") MultipartFile file,
                                              Authentication authentication) throws IOException {
        Form form = new Form(1, "Civil Extract");
        return ResponseEntity.ok(attachmentService.uploadAttachment(file, authentication, form));
    }

    @PostMapping("/judicial")
    public ResponseEntity<String> uploadJudicial(@RequestParam("file") MultipartFile file,
                                              Authentication authentication) throws IOException {
        Form form = new Form(2, "Judicial Extract of Records");
        return ResponseEntity.ok(attachmentService.uploadAttachment(file, authentication, form));
    }
}
