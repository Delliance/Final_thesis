package com.danielfinal.finalproject.repositories;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailboxRepository extends JpaRepository <Mailbox, Long> {

    Optional<Mailbox> findMailboxByUserUsername (String username);

}
