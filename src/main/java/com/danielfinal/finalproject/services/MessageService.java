package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public boolean checkIfMessageExistsId (long messageId){
        return messageRepository.findMessageById(messageId).isPresent();
    }

    public Message getMessageById (long messageId){
        checkIfMessageExistsId(messageId);
        return messageRepository.getMessageById(messageId);
    }

    public List<Message> getMessagesBySenderMailboxId(long senderMailboxId){
        return messageRepository.getMessagesBySenderMailboxId (senderMailboxId);
    }

    public List<Message> getMessagesByPrimaryReceptorMailboxId(long primaryReceptorCopyMailboxId){
        return messageRepository.getMessagesByPrimaryReceptorMailboxId(primaryReceptorCopyMailboxId);
    }

    public List<Message> getMessagesByCarbonCopyMailboxId(long carbonCopyMailboxId){
        return messageRepository.getMessagesByBlindCarbonCopyMailboxId(carbonCopyMailboxId);
    }

    public List<Message> getMessagesByBlindCarbonCopyMailboxId(long blindCarbonCopyMailboxId){
        return messageRepository.getMessagesByBlindCarbonCopyMailboxId(blindCarbonCopyMailboxId);
    }

    public List<Message> getAllMessagesByIdAndCurrentUsername(long messageId){

        checkIfMessageExistsId(messageId);

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

        List <Message> allMessages = new ArrayList<>();

        allMessages.addAll(messageRepository.getMessagesByIdAndPrimaryReceptor(messageId,username));
        allMessages.addAll(messageRepository.getMessagesByIdAndCarbonCopy(messageId,username));
        allMessages.addAll(messageRepository.getMessagesByIdAndBlindCarbonCopy(messageId,username));

        return allMessages;
    }

    public void checkIfMessageByIdIsYoursByUsername(long messageId){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

        if (!messageRepository.getMessageById(messageId).getSender().equals(username)){
            throw new IllegalStateException("You're not the owner of this message");
        }
    }

    public void saveMessage (Message message){
        messageRepository.save(message);
    }

}
