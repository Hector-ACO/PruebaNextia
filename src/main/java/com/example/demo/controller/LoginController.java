package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity get(@RequestParam String userName, @RequestParam String password){
        Optional<User> verify = userRepository.findByUserName(userName);
        if (verify.isPresent()) {
            User user = verify.get();
            if (password.equals(user.getPassword())) {
                user.setToken(getToken(user));
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.badRequest().body("Usuario o Contraseña incorrecta");
    }

    private String getToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("USER");
        return "Bearer " + Jwts.builder().setId(String.valueOf(user.getId())).setSubject(user.getEmail())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey()).compact();
    }

    private Key getSignInKey() {
        String secretKey = "yQIwZxhuUURKIA7H3PPNZfT1u73qQpXFV0NgNxZwCTo4HadrhdXdhgVJg8s0bdLNPr1h2tJIyk25U7Qb";
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
