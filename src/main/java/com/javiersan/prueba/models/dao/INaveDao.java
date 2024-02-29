package com.javiersan.prueba.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.javiersan.prueba.models.entity.Nave;

public interface INaveDao extends PagingAndSortingRepository<Nave, Long> {
    
}
