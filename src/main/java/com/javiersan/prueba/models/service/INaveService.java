package com.javiersan.prueba.models.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.javiersan.prueba.models.entity.Nave;

public interface INaveService {

    public Page<Nave> findAll();

    public Nave save(Nave nave);

    public Nave findById(Long id);

    public void delete(Long id);

    public List<Nave> findByName(String term);
    
}
