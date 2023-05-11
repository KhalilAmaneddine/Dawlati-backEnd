package com.example.Dawlati.Controllers;

import com.example.Dawlati.Models.Attachment;
import com.example.Dawlati.Models.AuditLog;
import com.example.Dawlati.Models.Form;
import com.example.Dawlati.Models.User;
import com.example.Dawlati.Services.AttachmentService;
import com.example.Dawlati.Services.AuditService;
import com.example.Dawlati.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

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
