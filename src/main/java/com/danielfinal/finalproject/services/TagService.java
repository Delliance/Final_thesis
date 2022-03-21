package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

}
