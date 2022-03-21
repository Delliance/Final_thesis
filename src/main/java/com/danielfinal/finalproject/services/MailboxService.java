package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.repositories.MailboxRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MailboxService {

    private final MailboxRepository mailboxRepository;

    public boolean checkIfMailboxExistByUserName (String username){
        return mailboxRepository.findMailboxByUserUsername(username).isPresent();
    }

    public boolean checkIfMailboxExistById(long id){
        return mailboxRepository.findMailboxById(id).isPresent();
    }

    public Mailbox getMailboxByUsername(String username){

        if (!checkIfMailboxExistByUserName(username)){
            throw new IllegalStateException("The username: "+username+" does not exist");
        }

        return mailboxRepository.getMailboxByUserUsername(username);

    }

}
