package com.example.Dawlati.services;

import com.example.Dawlati.annotations.Audit;
import com.example.Dawlati.models.*;
import com.example.Dawlati.repositories.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AttachmentService {

    private AttachmentRepository attachmentRepository;
    private final UserService userService;
    private final AuditService auditService;
    private final String path = "C:/Users/Admin/Desktop/DawlatiAttachments";

    @Audit(action = AuditLogAction.Upload, details = "User Uploaded a Document")
    public String uploadAttachment(MultipartFile file, Authentication authentication, Form form)
            throws IOException {
        User user = userService.findByEmail(authentication.getName());
        String filePath = path + "/" + file.getOriginalFilename();
        Attachment attachment = new Attachment(filePath, form, user);
        file.transferTo(new java.io.File(filePath));
        Attachment fileData = attachmentRepository.save(attachment);
        return fileData.getFilePath();
    }

}
