package com.javiersan.prueba.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javiersan.prueba.models.dao.INaveDao;
import com.javiersan.prueba.models.entity.Nave;;


public class NaveServiceImpl implements INaveService {
  
     @Autowired
    private INaveDao naveDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Nave> findAll(Pageable pageRequest) {
        return  naveDao.findAll(pageRequest);
        
    }

    @Override
    @Transactional
    public Nave save(Nave nave) {
       return naveDao.save(nave);
    }


    @Cacheable("naves")
	@Override
	@Transactional(readOnly = true)
	public List<Nave> findByName(String term) {
		return naveDao.findByNameLikeIgnoreCase("%" + term + "%");
	}

    @Override
    @Transactional(readOnly = true)
    public Nave findById(Long id) {
       return naveDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
      naveDao.deleteById(id);
    }
}
