package com.example.demo.controller;

import com.example.demo.model.Documents;
import com.example.demo.repository.DocumentsRepository;
import com.example.demo.service.DocumentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("docs")
public class DocumentsController extends BaseController<Documents>{

    public DocumentsController(DocumentsRepository documentsRepository) { super(documentsRepository); }

    @Autowired
    DocumentsService documentsService;

    @Override
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Documents entity, Errors errors){
        return ResponseEntity.status(405).build();
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Documents entity, Errors errors){
        return ResponseEntity.status(405).build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        documentsService.deleteFile(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity upload(@RequestPart(value = "files") List<MultipartFile> files, @RequestParam(value="names") List<String> names){
        if(documentsService.validaNames(names)){
            return ResponseEntity.badRequest().body("Uno o varios de los nombres ya se encuentran registraros");
        }
        documentsService.save(files, names);
        return ResponseEntity.noContent().build();
    }

}
