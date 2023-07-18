//package com.ams.backend.controller;
//
//import com.ams.backend.entity.CommonAudit;
//import com.ams.backend.service.CommonAuditService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(CommonAuditController.class)
//public class CommonAuditControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CommonAuditService commonAuditService;
//
//    @Test
//    public void getAllCommonAuditTest() throws Exception {
//
//        List<CommonAudit> commonAudits = new ArrayList<>();
//        Mockito.when(commonAuditService.getAllCommonAudits()).thenReturn(commonAudits);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/commonAudit"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
////    public void getCommonAuditByIdTest() throws Exception {
////        CommonAudit commonAudit = new CommonAudit(1, "Gestion Laboral S.A.");
////        Mockito.when(commonAuditService.getCommonAuditById(1)).thenReturn(commonAudit);
////
////        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/commonAudit/1"))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.commonAudit").value("Gestion Laboral S.A."));
////    }
//
////    @Test
////    public void createCommonAuditTest() throws Exception {
////        CommonAudit savedCommonAudit = new CommonAudit(1, "Gestion Laboral S.A.");
////        Mockito.when(commonAuditService.createCommonAudit(savedCommonAudit)).thenReturn(savedCommonAudit);
////
////        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/commonAudit")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"branch\":\"commonAudit\"}"))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////    }
////
////    @Test
////    public void updateCommonAuditTest() throws Exception {
////        CommonAudit updatedCommonAudit = new CommonAudit(1, "Gestion Logistica S.A.");
////
////        Mockito.when(commonAuditService.updateCommonAudit(1, updatedCommonAudit)).thenReturn(updatedCommonAudit);
////
////        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/commonAudit/1")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"id\":\"1\",\"commonAudit\":\"Gestion Logistica S.A.\"}"))
////                .andExpect(MockMvcResultMatchers.status().isOk());
////
////        Mockito.verify(commonAuditService, Mockito.times(1))
////                .updateCommonAudit(ArgumentMatchers.anyInt(), ArgumentMatchers.any(CommonAudit.class));
////    }
////
////    @Test
////    public void deleteCommonAuditTest() throws Exception {
////        int id = 1;
////
////        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/commonAudit/{id}", id))
////                .andExpect(MockMvcResultMatchers.status().isNoContent());
////
////        Mockito.verify(commonAuditService, Mockito.times(1)).deleteCommonAudit(id);
////    }
//
//}
