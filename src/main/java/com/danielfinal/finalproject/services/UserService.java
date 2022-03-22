package com.danielfinal.finalproject.services;

import com.danielfinal.finalproject.entities.Mailbox;
import com.danielfinal.finalproject.entities.User;
import com.danielfinal.finalproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }

    public boolean checkIfUserExistsByUsername (String username){

        Optional <User> userOptional = userRepository.findUserByUsername(username);

        return userOptional.isPresent();

    }

    public boolean checkIfUserExistsByDni (String dni){

        Optional <User> userOptional = userRepository.findUserByDni(dni);

        return userOptional.isPresent();

    }

    public String singUpUser (User user){

        if (checkIfUserExistsByUsername(user.getUsername())){
            throw new IllegalStateException("The username: "+user.getUsername()+" is already taken");
        }

        if (checkIfUserExistsByDni(user.getDni())){
            throw new IllegalStateException("The dni: "+user.getDni()+" is already already registered");
        }

//        THESE ARE COMMENTED ONLY FOR TESTING

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        user.setMailBox(new Mailbox());

        userRepository.save(user);

        return "it works!!";

    }

}
