package com.example.demo.repository;

import com.example.demo.model.Documents;

import java.util.List;

public interface DocumentsRepository extends BaseRepository<Documents>{
    List<Documents> findByNameIn(List<String> names);
}
