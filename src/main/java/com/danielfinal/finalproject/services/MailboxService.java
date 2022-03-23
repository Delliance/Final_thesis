package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.repositories.MailboxRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MailboxService {

    private final MailboxRepository mailboxRepository;

    private final MessageService messageService;
    private final UserService userService;

    public void checkIfMailboxExistByUserName (String username){

        if(mailboxRepository.findMailboxByUserUsername(username).isEmpty()){
            throw new IllegalStateException("The username: "+username+" does not exist");
        }

    }

    public Mailbox getMailboxByUsername(String username){

        checkIfMailboxExistByUserName(username);

        return mailboxRepository.getMailboxByUserUsername(username);

    }

    public void sendMessage (Message message){

        if (!userService.checkIfUserExistsByUsername(message.getPrimaryReceptor())){
            throw new IllegalStateException("Receptor username does not exist");
        }

        if (message.getCarbonCopy()!=null){
            if(!userService.checkIfUserExistsByUsername(message.getCarbonCopy())){
                throw new IllegalStateException("Carbon copy username does not exist");
            }
        }

        if (message.getBlindCarbonCopy()!=null){
            if(!userService.checkIfUserExistsByUsername(message.getBlindCarbonCopy())){
                throw new IllegalStateException("Blind carbon copy username does not exist");
            }
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String sender;

        if(principal instanceof UserDetails){
            sender = ((UserDetails)principal).getUsername();
        }
        else {
            sender = principal.toString();
        }

//        This is here for testing, also remember to change the path in the WebSecurityConfig to "/api/v1*/**"
//        sender = "DGonzalez";

        userService.checkIfUserExistsByUsername(sender);

        message.setSender(sender);

        message.setSenderMailbox(getMailboxByUsername(sender));

        messageService.saveMessage(message);

    }

    public List <Message> getReceivedMessagesByMailBox(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

//        This is here for testing
//        username = "DGonzalez";

        checkIfMailboxExistByUserName(username);

        List <Message> allReceivedMessages = new ArrayList<>();

        allReceivedMessages.addAll(messageService.getMessagesByPrimaryReceptorMailboxId(getMailboxByUsername(username).getId()));
        allReceivedMessages.addAll(messageService.getMessagesByCarbonCopyMailboxId(getMailboxByUsername(username).getId()));
        allReceivedMessages.addAll(messageService.getMessagesByBlindCarbonCopyMailboxId(getMailboxByUsername(username).getId()));

        return allReceivedMessages;

    }

    public List <Message> getSentMessagesByMailBox(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

//        This is here for testing
//        username = "DGonzalez";

        checkIfMailboxExistByUserName(username);

        return messageService.getMessagesBySenderMailboxId(getMailboxByUsername(username).getId());

    }

}
