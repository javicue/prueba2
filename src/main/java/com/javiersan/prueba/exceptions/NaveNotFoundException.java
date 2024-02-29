package com.javiersan.prueba.exceptions;

public class NaveNotFoundException extends RuntimeException {
    
    public NaveNotFoundException(String message) {
        super(message);
    }
}
