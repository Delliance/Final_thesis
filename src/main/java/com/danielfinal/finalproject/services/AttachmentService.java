package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Attachment;
import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.repositories.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final MessageService messageService;

    public Attachment store(MultipartFile file, long messageId) throws IOException {

        if (!messageService.checkIfMessageExistsId(messageId)){
            throw new IllegalStateException("This message does not exist");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        Attachment attachment = new Attachment(filename, file.getContentType(), file.getBytes(), messageService.getMessageById(messageId));

        return attachmentRepository.save(attachment);
    }

    public Attachment getFiles(String id){
        return attachmentRepository.findById(id).get();
    }

    public Stream<Attachment> getFilesByEmailId (long id){
        return attachmentRepository.getAttachmentsByMessageId(id).stream();
    }

    public Stream<Attachment> getAllFiles(){
        return attachmentRepository.findAll().stream();
    }

}
