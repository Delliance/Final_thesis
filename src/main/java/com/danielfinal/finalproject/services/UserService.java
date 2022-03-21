package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.User;
import com.danielfinal.finalproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean checkIfUserExistsByUsername (String username){

        Optional <User> userOptional = userRepository.findUserByUsername(username);

        return userOptional.isPresent();

    }

    public boolean checkIfUserExistsByDni (String dni){

        Optional <User> userOptional = userRepository.findUserByDni(dni);

        return userOptional.isPresent();

    }

    public void createNewUser (User user){

        if (checkIfUserExistsByUsername(user.getUsername())){
            throw new IllegalStateException("The username: "+user.getUsername()+" is already taken");
        }

        if (checkIfUserExistsByDni(user.getDni())){
            throw new IllegalStateException("The dni: "+user.getDni()+" is already already registered");
        }

        userRepository.save(user);

    }

}
