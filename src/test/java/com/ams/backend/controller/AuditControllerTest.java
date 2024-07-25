package com.ams.backend.controller;

import com.ams.backend.entity.*;
import com.ams.backend.service.AuditService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@WebMvcTest(AuditController.class)
public class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService auditService;

    final private AuditType auditType = new AuditType(1,"Test");
    final private Audited audited = new Audited(1,"TEST");
    final private Audit audit = new Audit(
            1,
            LocalDate.now(),
            auditType,
            audited
    );

    @Test
    public void getAllAuditTest() throws Exception {

        List<Audit> audits = new ArrayList<>();
        Mockito.when(auditService.getAllAudit()).thenReturn(audits);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audit"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAuditByIdTest() throws Exception {
        Mockito.when(auditService.getAuditById(1)).thenReturn(audit);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void createAuditTest() throws Exception {
        int auditTypeId = 1;
        Audit createdAudit = new Audit(1, LocalDate.now(), auditType, audited);

        Mockito.when(auditService.createAudit(auditTypeId)).thenReturn(createdAudit);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/audit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(auditTypeId)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(auditService, times(1)).createAudit(auditTypeId);
        verifyNoMoreInteractions(auditService);
    }

    @Test
    public void updateAuditTest() throws Exception {
        int auditId = 1;
        Audit updatedAudit = new Audit(auditId, LocalDate.now(), auditType, audited);

        // Configurar el comportamiento del servicio mock
        Mockito.when(auditService.updateAudit(eq(auditId), argThat(new AuditMatcher(updatedAudit))))
                .thenReturn(updatedAudit);

        // Convertir el objeto Audit a JSON manualmente
        String jsonContent = "{\"id\":1,\"auditNumber\":99,\"auditDate\":\"" + updatedAudit.getAuditDate() + "\",\"idTipoAuditoria\":{\"id\":1,\"name\":\"Test\"},\"idAuditado\":{\"id\":1,\"name\":\"TEST\"}}";

        // Realizar la solicitud al controlador utilizando MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/audit/{id}", auditId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar las interacciones del servicio mock
        verify(auditService, times(1)).updateAudit(eq(auditId), argThat(new AuditMatcher(updatedAudit)));
        verifyNoMoreInteractions(auditService);
    }

    @Test
    public void deleteAuditTest() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/audit/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(auditService, times(1)).deleteAudit(id);
    }

}
