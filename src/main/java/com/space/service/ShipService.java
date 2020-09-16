package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import org.springframework.data.domain.Page;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ShipService {

    List<Ship> getAll();

    void save(Ship ship);

    void delete(Long id);

    Ship getById(Long id);


    Page<Ship> findAllPage(Pageable pageable);

    Page<Ship> findAllFiltered(ShipSpecification sSpec, Pageable pageable);

    Long count (ShipSpecification sSpec);

    List<Ship> getAll(ShipSpecification sSpec);
}
