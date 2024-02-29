package com.javiersan.prueba.models.service;


import org.springframework.security.core.userdetails.UserDetails;


public interface IUsuarioService {

    public UserDetails loadUserByUsername(String username);
    
}