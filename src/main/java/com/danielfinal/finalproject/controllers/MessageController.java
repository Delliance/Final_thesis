package com.danielfinal.finalproject.controllers;

import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.services.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;

    @PostMapping
    public String newMessage (@RequestBody Message message){
        messageService.sendMessage(message);
        return "Message sent";
    }

    @GetMapping(path = "/received")
    public List <String> getAllReceivedMessages(){

        List <Message> messageList = messageService.getReceivedMessages();

        List <String> info = new ArrayList<>();

        messageService.getReceivedMessages();

        messageList.forEach(message ->
                info.add(" Sender: "+message.getSender()+
                        " To: "+message.getPrimaryReceptor()+
                        " CC: "+message.getCarbonCopy()+
                        " Subject: "+message.getSubject()+
                        " Body: "+message.getBody())
                );

        return info;
    }

    @GetMapping(path = "/sent")
    public List <String> getAllSentMessages(){

        List <Message> messageList = messageService.getSentMessages();

        List <String> info = new ArrayList<>();

        messageService.getSentMessages();

        messageList.forEach(message ->
                info.add(" Sender: "+message.getSender()+
                        " To: "+message.getPrimaryReceptor()+
                        " CC: "+message.getCarbonCopy()+
                        " BCC: "+message.getBlindCarbonCopy()+
                        " Subject: "+message.getSubject()+
                        " Body: "+message.getBody())
                );

        return info;
    }

}
