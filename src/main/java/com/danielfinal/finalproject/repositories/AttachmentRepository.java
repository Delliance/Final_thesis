package com.danielfinal.finalproject.repositories;

import com.danielfinal.finalproject.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository <Attachment, String> {

    List<Attachment> getAttachmentsByMessageId (Long id);

}
