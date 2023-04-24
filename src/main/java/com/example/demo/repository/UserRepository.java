package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.Optional;

public interface UserRepository extends  BaseRepository<User>{

    Optional<User> findByUserName(String userName);

}
