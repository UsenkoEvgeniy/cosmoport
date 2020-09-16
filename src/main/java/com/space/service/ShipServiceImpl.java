package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepository;

    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> getAll() {
        return shipRepository.findAll();
    }

    @Override
    public void save(Ship ship) {
        shipRepository.save(ship);
    }

    @Override
    public void delete(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public Ship getById(Long id) {
        Optional<Ship> optionalShip= shipRepository.findById(id);
        if(!optionalShip.isPresent()) return null;
        return optionalShip.get();
    }


    @Override
    public Page<Ship> findAllPage(Pageable pageable) {
        return shipRepository.findAll(pageable);
    }

    @Override
    public Page<Ship> findAllFiltered(ShipSpecification sSpec, Pageable pageable) {
        return shipRepository.findAll(sSpec, pageable);
    }

    @Override
    public Long count(ShipSpecification sSpec) {
        return shipRepository.count(sSpec);
    }
    @Override
    public List<Ship> getAll(ShipSpecification sSpec) {
        return shipRepository.findAll(sSpec);
    }
}
