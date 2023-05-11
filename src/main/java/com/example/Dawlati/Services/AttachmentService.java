package com.example.Dawlati.Services;

import com.example.Dawlati.Models.Attachment;
import com.example.Dawlati.Models.AuditLog;
import com.example.Dawlati.Models.Form;
import com.example.Dawlati.Models.User;
import com.example.Dawlati.Repositories.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String uploadAttachment(MultipartFile file, Authentication authentication, Form form)
            throws IOException {
        User user = userService.findByEmail(authentication.getName());
        AuditLog auditLog = new AuditLog(LocalDateTime.now(), "User " + authentication.getName()
                + " uploaded a" + form.getFormName() + "attachment", "Upload", user);
        auditService.add(auditLog);
        String filePath = path + "/" + file.getOriginalFilename();
        Attachment attachment = new Attachment(filePath, form, user);
        file.transferTo(new java.io.File(filePath));
        Attachment fileData = attachmentRepository.save(attachment);
        return fileData.getFilePath();
    }
    /*public String uploadAttachment(Attachment attachment) {
        Attachment fileData = attachmentRepository.save(attachment);
        return fileData.getFilePath();
    }*/


}
