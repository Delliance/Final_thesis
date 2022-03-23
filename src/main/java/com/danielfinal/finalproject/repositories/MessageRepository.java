package com.danielfinal.finalproject.repositories;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long> {

    List <Message> getMessagesByIdAndCarbonCopy (long id, String carbonCopy);

    List <Message> getMessagesByIdAndBlindCarbonCopy (long id, String blindCarbonCopy);

    List <Message> getMessagesByIdAndPrimaryReceptor (long id, String carbonCopy);

    Optional <Message> findMessageById (long id);

    Message getMessageById (long id);

    List <Message> getMessagesBySenderMailboxId (long SenderMailboxId);

    List <Message> getMessagesByPrimaryReceptorMailboxId (long primaryReceptorMailBoxId);

    List <Message> getMessagesByBlindCarbonCopyMailboxId (long blindCarbonCopyMailBoxId);

}
