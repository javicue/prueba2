package com.javiersan.prueba.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class NaveServicePointcut {
       
        @Pointcut("execution(* com.javiersan.prueba.models.service.INaveService.findById)")
        public void loggerBeforePointCut(){}
}
