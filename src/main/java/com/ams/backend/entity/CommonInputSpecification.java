package com.ams.backend.entity;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static com.ams.backend.utils.SearchFilterHelper.getCommonInputSpecification;

public class CommonInputSpecification {

    public static Specification<CommonInput> getFilteredCommonInputs(
            String apellido, String nombre, String cuil, String legajo,
            String asignacion, Long idCliente, String uoc,
            Long idSucursal, LocalDate fechaIngreso, Long idCaracteristicas
    ) {
        return getCommonInputSpecification(apellido, nombre, cuil, legajo, asignacion, idCliente, uoc, idSucursal, fechaIngreso, idCaracteristicas);
    }
}
