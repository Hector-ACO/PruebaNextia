package com.example.demo.service;

import com.example.demo.property.FileProperties;
import com.example.demo.model.Documents;
import com.example.demo.repository.DocumentsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocumentsService {

    private final Path fileStorageLocation;

    @Autowired
    public DocumentsService(FileProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    DocumentsRepository documentsRepository;

    @Transactional
    public void save(List<MultipartFile> files,List<String> names){
        if(files.size() != names.size()){
            throw new RuntimeException("El numero de documenos no concuerda con el numero de nombres");
        }
        for (int i = 0; i < files.size(); i++) {
            uploadFile(files.get(i), names.get(i));
        }
    }

    public void uploadFile(MultipartFile file, String name){
        Documents doc = new Documents();
        doc.setName(name);
        doc.setPath(storeFile(file, name));
        documentsRepository.save(doc);
    }

    public String storeFile(MultipartFile file, String name) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String newName = name + extension;
        try {
            Path targetLocation = this.fileStorageLocation.resolve(newName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Boolean validaNames(List<String> names){
        List<Documents> d = documentsRepository.findByNameIn(names);
        return d.size() > 0;
    }

    public void deleteFile(Long id){
        Optional<Documents> verify = documentsRepository.findById(id);
        if(verify.isPresent()){
            Path file = Paths.get("./upload/"+verify.get().getPath());
            try {
                Files.delete(file);
            }catch (Exception e){
                log.error(e.getMessage());
            }
            documentsRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se encontro el documento con id " + id );
        }
    }

}
