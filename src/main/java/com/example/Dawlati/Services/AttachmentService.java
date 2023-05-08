package com.example.Dawlati.Services;

import com.example.Dawlati.Models.Attachment;
import com.example.Dawlati.Repositories.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttachmentService {

    private AttachmentRepository attachmentRepository;
    public String uploadAttachment(Attachment attachment) {
        Attachment fileData = attachmentRepository.save(attachment);
        return fileData.getFilePath();
    }
}
