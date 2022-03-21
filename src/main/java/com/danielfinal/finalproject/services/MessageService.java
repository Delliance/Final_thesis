package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;
    private final AttachmentService attachmentService;

    public void sendMessage (Message message){

        if (!userService.checkIfUserExistsByUsername(message.getPrimaryReceptor())){
            throw new IllegalStateException("Receptor username does not exist");
        }

    }

}
