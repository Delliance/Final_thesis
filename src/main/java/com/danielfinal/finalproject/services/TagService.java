package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Message;
import com.danielfinal.finalproject.entities.Tag;
import com.danielfinal.finalproject.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    private final MessageService messageService;

    public List<Message> getMessagesByTagName (String tagName){
        return messageService.getAllMessagesByIdAndCurrentUsername(tagRepository.getTagByName(tagName).getMessage().getId());
    }

    public void createNewTag (Tag tag) {

        if (tagRepository.findTagByName(tag.getName()).isPresent()){
            throw new IllegalStateException("Tag with the name: "+tag.getName()+" already exists");
        }

        tagRepository.save(tag);
    }

    public void tagMessageWithExistingTag(Tag tag, long messageId){

        if (!messageService.checkIfMessageExistsId(messageId)){
            throw new IllegalStateException("The message with id: "+messageId+" does not exist");
        }

        if (tagRepository.findTagByName(tag.getName()).isPresent()&&tagRepository.getTagByName(tag.getName()).getMessage()==null){

            tagRepository.updateMessageIdByTagName(messageId, tag.getName());

        }
        else {

            tag.setMessage(messageService.getMessageById(messageId));
            tagRepository.save(tag);

        }



    }

}
