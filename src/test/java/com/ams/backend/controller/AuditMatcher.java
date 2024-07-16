package com.ams.backend.controller;

import com.ams.backend.entity.Audit;
import org.mockito.ArgumentMatcher;

public class AuditMatcher implements ArgumentMatcher<Audit> {
    private final Audit expected;

    public AuditMatcher(Audit expected) {
        this.expected = expected;
    }

    @Override
    public boolean matches(Audit actual) {
        // Comparar los campos relevantes para determinar si son iguales
        return actual.getId() == expected.getId() &&
                actual.getAuditNumber() == expected.getAuditNumber() &&
                actual.getAuditDate().isEqual(expected.getAuditDate()) &&
                actual.getIdTipoAuditoria().getId() == expected.getIdTipoAuditoria().getId() &&
                actual.getIdAuditado().getId() == expected.getIdAuditado().getId();
    }
}



