package com.space.service;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ShipSpecification implements Specification<Ship> {
    private ShipSearch criteria;

    public ShipSpecification(ShipSearch criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Path<String> name = root.get("name");
        Path<String> planet = root.get("planet");
        final List<Predicate> predicates = new ArrayList<Predicate>();
        if(criteria.getName() != null){
            predicates.add(cb.like (name,"%" + criteria.getName() + "%"));
        }
        if(criteria.getPlanet() != null){
            predicates.add(cb.like(planet, "%" + criteria.getPlanet() + "%"));
        }
        if(criteria.getShipType() != null) {
            predicates.add(cb.equal(root.get("shipType"), criteria.getShipType()));
        }
        if(criteria.getAfter() != null) {
            LocalDate lDate = Instant.ofEpochMilli(criteria.getAfter()).atZone(ZoneId.systemDefault()).toLocalDate();
            lDate = LocalDate.of(lDate.getYear(), 1,1);
            predicates.add(cb.greaterThanOrEqualTo(root.get("prodDate"), java.sql.Date.valueOf(lDate)));
        }
        if(criteria.getBefore() != null) {
            LocalDate lDate = Instant.ofEpochMilli(criteria.getBefore()).atZone(ZoneId.systemDefault()).toLocalDate();
            lDate = LocalDate.of(lDate.getYear(), 12,31);
            predicates.add(cb.lessThanOrEqualTo(root.get("prodDate"), java.sql.Date.valueOf(lDate)));
        }
        if(criteria.getIsUsed() != null) {
            predicates.add(cb.equal(root.get("isUsed"), criteria.getIsUsed()));
        }
        if(criteria.getMinSpeed() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("speed"), criteria.getMinSpeed()));
        }
        if(criteria.getMaxSpeed() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("speed"), criteria.getMaxSpeed()));
        }
        if(criteria.getMinCrewSize() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("crewSize"), criteria.getMinCrewSize()));
        }
        if(criteria.getMaxCrewSize() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("crewSize"), criteria.getMaxCrewSize()));
        }
        if(criteria.getMinRating() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), criteria.getMinRating()));
        }
        if(criteria.getMaxRating() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("rating"), criteria.getMaxRating()));
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
