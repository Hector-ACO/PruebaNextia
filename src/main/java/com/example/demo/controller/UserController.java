package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController<User>{

    public UserController(UserRepository userRepository) { super(userRepository); }

    @Autowired
    UserService userService;

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity update(@RequestBody User entity, Errors errors){
        if(entity.getUserName() == null || entity.getUserName().equals("")){
            return ResponseEntity.badRequest().body("El userName no deve estar vacio");
        }
        return ResponseEntity.ok(userService.update(entity));
    }

}
