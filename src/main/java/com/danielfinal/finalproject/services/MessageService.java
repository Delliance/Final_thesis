package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;
    private final MailboxService mailboxService;
    private final AttachmentService attachmentService;

    public void sendMessage (Message message){

        if (!userService.checkIfUserExistsByUsername(message.getPrimaryReceptor())){
            throw new IllegalStateException("Receptor username does not exist");
        }

//        TODO: conditional to check if the other receptors exist, if they are null, then don't

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

//        This is here for testing, also remember to change the path in the WebSecurityConfig to "/api/v1*/**"
//        username = "DGonzalez";

        userService.checkIfUserExistsByUsername(username);

        message.setSender(username);

        message.setMailbox(mailboxService.getMailboxByUsername(username));

        messageRepository.save(message);

    }

    public List<Message> getReceivedMessages (){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

//        This is here for testing, also remember to change the path in the WebSecurityConfig to "/api/v1*/**"
//        username = "DGonzalez";

        userService.checkIfUserExistsByUsername(username);

        List <Message> primary = messageRepository.getMessagesByPrimaryReceptor(username);
        List <Message> copy = messageRepository.getMessagesByCarbonCopy(username);
        List <Message> blindCopy = messageRepository.getMessagesByBlindCarbonCopy(username);

        List <Message> messageList = new ArrayList<>();

        messageList.addAll(primary);
        messageList.addAll(copy);
        messageList.addAll(blindCopy);

        return messageList;
    }

    public List<Message> getSentMessages (){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }
        else {
            username = principal.toString();
        }

//        This is here for testing
        username = "DGonzalez";

        userService.checkIfUserExistsByUsername(username);

        return messageRepository.getMessagesBySender(username);
    }

}
