package com.javiersan.prueba.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class idNegativoAspect {
    

    private Logger logger = LoggerFactory.getLogger(this.getClass());

  
    @Before("NaveServicePointcut.loggerBeforePointCut()") 
    public void loggerBefore(JoinPoint jointPoint) {

        
        String method = jointPoint.getSignature().getName();

        String args = Arrays.toString(jointPoint.getArgs());
        
        logger.info("Antes: " + method + " con los argumentos " + args);

    }
}
