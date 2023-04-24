package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User update(User entity){
        Optional<User> verify = findById(entity.getId());
        if(verify.isPresent()){
            User user = verify.get();
            user.setUserName(entity.getUserName());
            user.setEmail(entity.getEmail());
            return userRepository.save(user);
        }
        throw new RuntimeException("No se encontro el usuario con id " + entity.getId() );
    }

}
