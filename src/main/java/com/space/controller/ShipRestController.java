package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipSearch;
import com.space.service.ShipService;
import com.space.service.ShipSpecification;
import com.space.service.ShipValidation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest")
public class ShipRestController {
    private final ShipService shipService;

    public ShipRestController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id){
        if(id == null || id <= 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.shipService.getById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @PostMapping(value = "/ships", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> saveShip(@RequestBody Ship ship) {
        Ship valShip = ShipValidation.validateShip(ship);
        if (valShip == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.shipService.save(valShip);
        return new ResponseEntity<>(valShip, HttpStatus.OK);
    }

    @PostMapping(value = "/ships/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id,@RequestBody Ship ship){
        if(id == null || id <= 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship oldShip = shipService.getById(id);
        if (oldShip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(oldShip != null) {
            oldShip = ShipValidation.copyParams(ship, oldShip);
            oldShip = ShipValidation.validateShip(oldShip);
        }
        if (oldShip == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        this.shipService.save(oldShip);

        return new ResponseEntity<>(oldShip, HttpStatus.OK);
    }

    @DeleteMapping(value = "/ships/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = this.shipService.getById(id);
        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.shipService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/ships")
    public ResponseEntity<List<Ship>> getAllShips(@ModelAttribute ShipSearch shipSearch){

        ShipSpecification sSpec = new ShipSpecification(shipSearch);
        Page<Ship> shipFiltered = this.shipService.findAllFiltered(sSpec, shipSearch.getPageable());

        return new ResponseEntity<>(shipFiltered.getContent(),HttpStatus.OK);
    }

    @GetMapping (value = "/ships/count")
    public ResponseEntity<Integer> countShips(@ModelAttribute ShipSearch shipSearch){
        ShipSpecification sSpec = new ShipSpecification(shipSearch);
        Integer count = Math.toIntExact(shipService.count(sSpec));
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
