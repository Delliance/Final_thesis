package com.danielfinal.finalproject.repositories;

import com.danielfinal.finalproject.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository <Attachment, String> {
}
