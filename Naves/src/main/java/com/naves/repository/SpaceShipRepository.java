package com.naves.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naves.model.SpaceShip;

@Repository
public interface SpaceShipRepository extends JpaRepository<SpaceShip, Long> {
	Page<SpaceShip> findByNameContaining(String name, Pageable pageable);
}
