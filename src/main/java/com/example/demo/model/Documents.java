package com.example.demo.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Documents extends BaseModel {

    private String path;

}
