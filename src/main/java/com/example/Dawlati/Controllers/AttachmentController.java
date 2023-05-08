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
    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;
    private final String path = "C:/Users/Admin/Desktop/ProjectImages";
    @PostMapping("/civil")
    public ResponseEntity<String> uploadCivil(@RequestParam("document") MultipartFile file,
                                              Authentication authentication) throws IOException {
        User user = userService.findByEmail(authentication.getName());
        Form form = new Form(1, "Civil Extract");
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " uploaded a Civil extract attachment", "Upload", user);
        auditService.add(auditLog);
        String filePath = path + "/" + file.getOriginalFilename();
        Attachment attachment = new Attachment(filePath, form, user);
        file.transferTo(new java.io.File(filePath));
        return ResponseEntity.ok(attachmentService.uploadAttachment(attachment));
    }

    @PostMapping("/judicial")
    public ResponseEntity<String> uploadJudicial(@RequestParam("document") MultipartFile file,
                                              Authentication authentication) throws IOException {
        User user = userService.findByEmail(authentication.getName());
        Form form = new Form(2, "Judicial Extract of Records");
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " uploaded a Judicial extract attachment", "Upload", user);
        auditService.add(auditLog);
        String filePath = path + "/" + file.getOriginalFilename();
        Attachment attachment = new Attachment(filePath, form, user);
        file.transferTo(new java.io.File(filePath));
        return ResponseEntity.ok(attachmentService.uploadAttachment(attachment));
    }
}
