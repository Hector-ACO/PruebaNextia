package com.example.demo.controller;

import com.example.demo.model.BaseModel;
import com.example.demo.repository.BaseRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public abstract class BaseController<MODEL extends BaseModel> {

    private final BaseRepository<MODEL> repository;

    public BaseController(BaseRepository<MODEL>repository){
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity get(@RequestParam(required = false) Long id){
        if(id != null){
            return ResponseEntity.ok(repository.findById(id));
        }
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody MODEL entity, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        return ResponseEntity.ok(repository.save(entity));
    }

    @Transactional
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody MODEL entity, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Optional<MODEL> verify = repository.findById(entity.getId());
        if(verify.isPresent()){
            return ResponseEntity.ok(repository.save(entity));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<MODEL> verify = repository.findById(id);
        if(verify.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
