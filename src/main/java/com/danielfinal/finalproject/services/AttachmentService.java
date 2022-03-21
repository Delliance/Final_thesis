package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Attachment;
import com.danielfinal.finalproject.repositories.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public Attachment store(MultipartFile file) throws IOException {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        Attachment attachment = new Attachment(filename, file.getContentType(), file.getBytes());

        return attachmentRepository.save(attachment);
    }

    public Attachment getFiles(String id){
        return attachmentRepository.findById(id).get();
    }

    public Stream<Attachment> getAllFiles(){
        return attachmentRepository.findAll().stream();
    }

}
