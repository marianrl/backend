package com.ams.backend.utils;

import com.ams.backend.entity.AfipInput;
import com.ams.backend.entity.CommonInput;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchFilterHelper {

    public static Specification<AfipInput> getAfipInputSpecification(String apellido, String nombre, String cuil, String legajo, String asignacion, Long idCliente, String uoc, Long idSucursal, LocalDate fechaIngreso, Long idCaracteristicas) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (apellido != null) {
                predicates.add(criteriaBuilder.equal(root.get("apellido"), apellido));
            }
            if (nombre != null) {
                predicates.add(criteriaBuilder.equal(root.get("nombre"), nombre));
            }
            if (cuil != null) {
                predicates.add(criteriaBuilder.equal(root.get("cuil"), cuil));
            }
            if (legajo != null) {
                predicates.add(criteriaBuilder.equal(root.get("legajo"), legajo));
            }
            if (asignacion != null) {
                predicates.add(criteriaBuilder.equal(root.get("asignacion"), asignacion));
            }
            if (idCliente != null) {
                predicates.add(criteriaBuilder.equal(root.get("idCliente"), idCliente));
            }
            if (uoc != null) {
                predicates.add(criteriaBuilder.equal(root.get("uoc"), uoc));
            }
            if (idSucursal != null) {
                predicates.add(criteriaBuilder.equal(root.get("idSucursal"), idSucursal));
            }
            if (fechaIngreso != null) {
                predicates.add(criteriaBuilder.equal(root.get("fechaIngreso"), fechaIngreso));
            }
            if (idCaracteristicas != null) {
                predicates.add(criteriaBuilder.equal(root.get("idCaracteristicas"), idCaracteristicas));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<CommonInput> getCommonInputSpecification(String apellido, String nombre, String cuil, String legajo, String asignacion, Long idCliente, String uoc, Long idSucursal, LocalDate fechaIngreso, Long idCaracteristicas) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (apellido != null) {
                predicates.add(criteriaBuilder.equal(root.get("apellido"), apellido));
            }
            if (nombre != null) {
                predicates.add(criteriaBuilder.equal(root.get("nombre"), nombre));
            }
            if (cuil != null) {
                predicates.add(criteriaBuilder.equal(root.get("cuil"), cuil));
            }
            if (legajo != null) {
                predicates.add(criteriaBuilder.equal(root.get("legajo"), legajo));
            }
            if (asignacion != null) {
                predicates.add(criteriaBuilder.equal(root.get("asignacion"), asignacion));
            }
            if (idCliente != null) {
                predicates.add(criteriaBuilder.equal(root.get("idCliente"), idCliente));
            }
            if (uoc != null) {
                predicates.add(criteriaBuilder.equal(root.get("uoc"), uoc));
            }
            if (idSucursal != null) {
                predicates.add(criteriaBuilder.equal(root.get("idSucursal"), idSucursal));
            }
            if (fechaIngreso != null) {
                predicates.add(criteriaBuilder.equal(root.get("fechaIngreso"), fechaIngreso));
            }
            if (idCaracteristicas != null) {
                predicates.add(criteriaBuilder.equal(root.get("idCaracteristicas"), idCaracteristicas));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
