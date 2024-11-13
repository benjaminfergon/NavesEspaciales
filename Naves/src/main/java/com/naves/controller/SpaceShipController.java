package com.naves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.naves.model.SpaceShip;
import com.naves.service.SpaceShipService;

public class SpaceShipController {

	@Autowired
	private SpaceShipService spaceShipService;

	@GetMapping("/spaceships")
	public Page<SpaceShip> getShipsByName(@RequestParam String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return spaceShipService.getShipsByName(name, page, size);
	}
	
	@GetMapping("/spaceships/{id}")
	public SpaceShip getShipById(@PathVariable Long id) {
		return spaceShipService.getShipById(id);
	}
	
	@GetMapping("/spaceships/search")
    public Page<SpaceShip> searchShipsByName(@RequestParam String name, Pageable pageable) {
        return spaceShipService.getShipsByName(name, pageable);
    }
	
	// Crear una nueva nave
    @PostMapping
    public SpaceShip createSpaceShip(@RequestBody SpaceShip spaceShip) {
        return spaceShipService.createSpaceShip(spaceShip);
    }

    // Modificar una nave existente
    @PutMapping("/{id}")
    public SpaceShip updateSpaceShip(@PathVariable Long id, @RequestBody SpaceShip spaceShipDetails) {
        return spaceShipService.updateSpaceShip(id, spaceShipDetails);
    }

    // Eliminar una nave
    @DeleteMapping("/{id}")
    public void deleteSpaceShip(@PathVariable Long id) {
        spaceShipService.deleteSpaceShip(id);
    }

}
