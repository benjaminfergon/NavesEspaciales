package com.naves.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceShipAspect {
	
    private static final Logger logger = LoggerFactory.getLogger(SpaceShipAspect.class);
    
    // Este método interceptará las llamadas a cualquier método que reciba un Long como parámetro.
    @Before("execution(* com.naves.service.SpaceShipService.getShipById(Long)) && args(id)")
    public void logIfIdIsNegative(Long id) {
        if (id < 0) {
            logger.warn("Se intentó acceder a una nave con un id negativo: {}", id);
        }
    }


}
