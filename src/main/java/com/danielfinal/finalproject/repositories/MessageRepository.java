package com.danielfinal.finalproject.repositories;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long> {

//    List<Message> getMessagesByMailbox (Mailbox mailbox);

    List<Message> getMessagesBySender (String sender);

    List<Message> getMessagesByPrimaryReceptor (String primaryReceptor);

    List <Message> getMessagesByCarbonCopy (String carbonCopy);

    List <Message> getMessagesByBlindCarbonCopy (String blindCarbonCopy);

}
