package com.danielfinal.finalproject.controllers;

import com.danielfinal.finalproject.entities.Attachment;
import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.entities.Tag;
import com.danielfinal.finalproject.messageattachment.ResponseFile;
import com.danielfinal.finalproject.messageattachment.ResponseMessage;
import com.danielfinal.finalproject.services.AttachmentService;
import com.danielfinal.finalproject.services.MessageService;
import com.danielfinal.finalproject.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/message")
@AllArgsConstructor
public class MessageController {

    private MessageService messageService;
    private AttachmentService attachmentService;
    private TagService tagService;

    @PostMapping
    public String newMessage (@RequestBody Message message){
        messageService.sendMessage(message);
        return "Message sent";
    }

    @PostMapping(path = "/attachment/upload/{messageId}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file, @PathVariable("messageId") long messageId){

//        TODO: check that the user is the same as the one for the message, that way you can't attach to emails that are not yours

        String message;
        try {
            attachmentService.store(file,messageId);
            message = "File "+ file.getOriginalFilename() +"attached successfully to the email";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        catch (Exception e){
            message = "Could not upload the file";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping(path = "/received")
    public List <String> getAllReceivedMessages(){

        List <Message> messageList = messageService.getReceivedMessages();

        List <String> info = new ArrayList<>();

        messageService.getReceivedMessages();

        messageList.forEach(message ->
                info.add("Message ID: "+message.getId()+
                        " Sender: "+message.getSender()+
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

        messageList.forEach(message ->
                info.add("Message ID: "+message.getId()+
                        " Sender: "+message.getSender()+
                        " To: "+message.getPrimaryReceptor()+
                        " CC: "+message.getCarbonCopy()+
                        " BCC: "+message.getBlindCarbonCopy()+
                        " Subject: "+message.getSubject()+
                        " Body: "+message.getBody())
                );

        return info;
    }

    @Transactional
    @GetMapping(path = "/attachment/{messageId}")
    public ResponseEntity<List<ResponseFile>> getAttachmentsByReceivedEmail(@PathVariable("messageId") long messageId){

//        TODO: check that the user is the same as the any of the message receptors, that way you can't open emails that are not yours

        List<ResponseFile> files = attachmentService.getFilesByEmailId(messageId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/received/{messageId}/")
                    .path(dbFile.getId())
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping(path = "/attachment/attachmentId/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable("id") String id){
        Attachment attachment = attachmentService.getFiles(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName()+"\"")
                .body(attachment.getData());
    }

        @PostMapping(path = "/tag/new")
    public String createNewTag (@RequestBody Tag tag){

//        TODO: check that only messages you're the recipient are tagged, if there are none, then send exception

        tagService.createNewTag(tag);
        return "Tag created successfully";
    }

    @PutMapping(path = "/tag/{messageId}")
    public String tagMessage (@RequestBody Tag tag, @PathVariable("messageId") long messageId){

//        TODO: check that you can only tag messages you're the receiver

        tagService.tagMessageWithExistingTag(tag, messageId);

        return "Message tagged successfully";

    }

    @GetMapping(path = "/tag/{tagName}")
    public List <String> getMessagesByTag (@PathVariable("tagName") String tagName){

        //        TODO: check that only your messages with the tag you decided appear

        List<Message> messageList = tagService.getMessagesByTagName(tagName);

        List <String> info = new ArrayList<>();

        messageList.forEach(message ->
                info.add("Tag: "+tagName+
                        " Message ID: "+message.getId()+
                        " Sender: "+message.getSender()+
                        " To: "+message.getPrimaryReceptor()+
                        " CC: "+message.getCarbonCopy()+
                        " BCC: "+message.getBlindCarbonCopy()+
                        " Subject: "+message.getSubject()+
                        " Body: "+message.getBody())

                );

        return info;

    }


}
