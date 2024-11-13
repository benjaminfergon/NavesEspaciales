package com.naves.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naves.Exceptions.SpaceShipNotFoundException;
import com.naves.model.SpaceShip;
import com.naves.repository.SpaceShipRepository;

@Service
public class SpaceShipService {

	@Autowired
	private SpaceShipRepository spaceShipRep;

	public Page<SpaceShip> getShipsByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return spaceShipRep.findByNameContaining(name, pageable);
	}

	public Page<SpaceShip> getShipsByName(String name, Pageable pageable) {
		return spaceShipRep.findByNameContaining(name, pageable);
	}

	@Cacheable(value = "spaceships", key = "#id")
	public SpaceShip getShipById(Long id) {
		return spaceShipRep.findById(id).orElseThrow(() -> new SpaceShipNotFoundException(id));
	}

	@CachePut(value = "spaceships", key = "#spaceship.id")
	public SpaceShip updateShip(SpaceShip spaceship) {
		return spaceShipRep.save(spaceship);
	}

	@CacheEvict(value = "spaceships", key = "#id")
	public void deleteShip(Long id) {
		spaceShipRep.deleteById(id);
	}

	// Crear una nueva nave
	public SpaceShip createSpaceShip(SpaceShip spaceShip) {
		return spaceShipRep.save(spaceShip);
	}

	// Modificar una nave existente
	public SpaceShip updateSpaceShip(Long id, SpaceShip spaceShipDetails) {
		SpaceShip existingSpaceShip = spaceShipRep.findById(id).orElseThrow(() -> new SpaceShipNotFoundException(id));

		// Actualizar los detalles de la nave
		existingSpaceShip.setName(spaceShipDetails.getName());
		existingSpaceShip.setSeries(spaceShipDetails.getSeries());
		existingSpaceShip.setType(spaceShipDetails.getType());

		return spaceShipRep.save(existingSpaceShip);
	}

	// Eliminar una nave
	public void deleteSpaceShip(Long id) {
		SpaceShip existingSpaceShip = spaceShipRep.findById(id).orElseThrow(() -> new SpaceShipNotFoundException(id));

		spaceShipRep.delete(existingSpaceShip);
	}

}
