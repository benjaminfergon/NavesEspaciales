package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import com.naves.Exceptions.SpaceShipNotFoundException;
import com.naves.app.NavesApplication;
import com.naves.model.SpaceShip;
import com.naves.repository.SpaceShipRepository;
import com.naves.service.SpaceShipService;

@SpringBootTest(classes = NavesApplication.class) // Especifica la clase principal si no se detecta automáticamente
@ExtendWith(MockitoExtension.class) // Necesario para usar Mockito con JUnit 5

public class NavesApplicationTests {

	@Mock
	private SpaceShipRepository spaceShipRep; // Simulamos el repositorio

	@InjectMocks
	private SpaceShipService spaceShipService; // Inyectamos el servicio que usa el repositorio simulado
	
	@Mock
    private Logger logger;  // Mock del logger

	// Prueba para obtener una nave por su ID

	@Test
	public void testGetShipById() {
		Long shipId = 1L;
		SpaceShip spaceship = new SpaceShip(shipId, "ALA-X", "Star Wars", "Combate");

		// Definimos el comportamiento del mock
		when(spaceShipRep.findById(shipId)).thenReturn(Optional.of(spaceship));

		// Llamamos al método del servicio
		SpaceShip result = spaceShipService.getShipById(shipId);

		// Verificamos que el resultado es el esperado
		assertNotNull(result);
		assertEquals(shipId, result.getId());
		assertEquals("X-Wing", result.getName());
	}

	// Prueba para crear una nueva nave espacial
	@Test
	public void testCreateSpaceShip() {
		// Arrange: Configura el escenario de prueba
		SpaceShip spaceShip = new SpaceShip(1L, "ALA-X", "Star Wars", "Combate");
		when(spaceShipRep.save(any(SpaceShip.class))).thenReturn(spaceShip);

		// Act: Llama al método a probar
		SpaceShip createdSpaceShip = spaceShipService.createSpaceShip(spaceShip);

		// Assert: Verifica los resultados
		assertNotNull(createdSpaceShip); 
		assertEquals("ALA-X", createdSpaceShip.getName()); 
		assertEquals("Star Wars", createdSpaceShip.getSeries()); 
		assertEquals("Combate", createdSpaceShip.getType()); 
		verify(spaceShipRep, times(1)).save(spaceShip); 
	}
	
	// Prueba para manejar el caso en que no se encuentra la nave
    @Test
    public void testGetSpaceShipById_NotFound() {
        
        when(spaceShipRep.findById(1L)).thenReturn(Optional.empty());

        assertThrows(SpaceShipNotFoundException.class, () -> spaceShipService.getShipById(1L));
        verify(spaceShipRep, times(1)).findById(1L); // Verifica que findById() se haya llamado una vez
    }
    
    @Test
    void testGetShipById_withCaching() {
        Long id = 1L;
        SpaceShip spaceship = new SpaceShip(id, "ALA-X", "Star Wars", "Combate");
        
        // Simulamos el comportamiento del repositorio
        when(spaceShipRep.findById(id)).thenReturn(Optional.of(spaceship));
        
        // Llamamos al método por primera vez (esto debería ser cacheado)
        SpaceShip result = spaceShipService.getShipById(id);
        assertNotNull(result);
        
        // Llamamos nuevamente (esto debería devolver el valor cacheado)
        SpaceShip resultFromCache = spaceShipService.getShipById(id);
        assertSame(result, resultFromCache);
    }
    
    // Prueba para el Id negativo
	/*
	 * @Test void testLogNegativeId() { // Simulamos el comportamiento de
	 * SpaceShipService Long negativeId = -1L;
	 * when(spaceShipService.getShipById(negativeId)).thenThrow(new
	 * IllegalArgumentException("ID negativo"));
	 * 
	 * // Llamamos al método para verificar si se loggea el mensaje
	 * spaceShipService.getShipById(negativeId);
	 * 
	 * // Verificamos que el aspecto haya registrado una advertencia // Usamos un
	 * logger mockeado para verificar si se ejecuta la acción de log
	 * logger.warn("Se intentó acceder a una nave con un id negativo: {}",
	 * negativeId); }
	 */

}
