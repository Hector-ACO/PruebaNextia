package com.example.demo.repository;

import com.example.demo.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<MODEL extends BaseModel> extends JpaRepository<MODEL, Long> {
}
