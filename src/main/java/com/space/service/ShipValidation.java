package com.space.service;

import com.space.model.Ship;

import java.util.Calendar;
import java.util.Date;

public class ShipValidation {

    public static Ship validateShip(Ship ship) {
        if (ship == null || ship.getShipType() == null) {
            return null;
        }
        if (ship.isUsed() == null) {
            ship.setUsed(false);
        }
        String name = ship.getName();
        if(name==null || name.isEmpty() || name.length() > 50){
            return null;
        }
        String planet = ship.getPlanet();
        if(planet == null || planet.isEmpty() || planet.length() > 50) {
            return null;
        }
        Date date = ship.getProdDate();
        if(date.before(new Date(2800-1900, Calendar.JANUARY,0)) || date.after(new Date(3019-1900, Calendar.DECEMBER,31))) {
            return null;
        }
        if(ship.getSpeed() == null) return null;
        double speed = Math.round(ship.getSpeed()*100)/100.0;
        if(speed < 0.01 || speed > 0.99) {
            return null;
        }
        ship.setSpeed(speed);
        int crewSize = ship.getCrewSize();
        if (crewSize < 1 || crewSize > 9999) {
            return null;
        }

        ship.setRating(ship.recalc());

        return ship;
    }

    public static Ship copyParams (Ship newShip,Ship oldShip){
        if (newShip.getName() != null) {
            oldShip.setName(newShip.getName());
        }
        if(newShip.getPlanet() != null) {
            oldShip.setPlanet(newShip.getPlanet());
        }
        if(newShip.getShipType() != null) {
            oldShip.setShipType(newShip.getShipType());
        }
        if(newShip.getProdDate() != null) {
            oldShip.setProdDate(newShip.getProdDate());
        }
        if(newShip.isUsed() != null) {
            oldShip.setUsed(newShip.isUsed());
        }
        if(newShip.getSpeed() != null) {
            oldShip.setSpeed(newShip.getSpeed());
        }
        if(newShip.getCrewSize() != null) {
            oldShip.setCrewSize(newShip.getCrewSize());
        }
        return oldShip;
    }
}
