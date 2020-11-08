package com.github.voragoth.drugstores.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.voragoth.drugstores.facade.AdminDivisionsFacade;
import com.github.voragoth.drugstores.handler.ErrorMessagesConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminDivisionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminDivisionsFacade facade;

    @Value("${drugstores.defaultRegion:7}")
    private Byte defaultRegion;

    @Test
    @DisplayName("Test unitario api/v1/comunas esperando resultado OK")
    void getCommunesShouldReturnOK() throws Exception {
        Map<String, Integer> communes = new HashMap<>();
        communes.put("Comuna de prueba", 1);
        doReturn(communes).when(facade).getCommunes(any(Byte.class));
        this.mockMvc
                .perform(get("/api/v1/comunas").param("region", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Map<?,?> actualObject = objectMapper.readValue(json, Map.class);
                    assertEquals(actualObject, communes);
                });
    }

    @Test
    @DisplayName("Test unitario api/v1/comunas esperando resultado OK para region por defecto")
    void getCommunesShouldReturnOKDefReg() throws Exception {
        Map<String, Integer> communes = new HashMap<>();
        communes.put("Comuna de prueba", 1);
        doReturn(communes).when(facade).getCommunes(eq(defaultRegion));
        this.mockMvc
                .perform(get("/api/v1/comunas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Map<?, ?> actualObject = objectMapper.readValue(json, Map.class);
                    assertEquals(actualObject, communes);
                });
    }

    @Test
    @DisplayName("Test unitario api/v1/comunas esperando resultado 400 por region 0 o negativa")
    void getCommunesShouldReturnBadRequestNonPositive() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/comunas").param("region", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorMessagesConstants.REGION_MUST_BE_POSITIVE));
    }

    @Test
    @DisplayName("Test unitario api/v1/regiones esperando resultado OK")
    void getRegionsShouldReturnOK() throws Exception {
        Map<String, Integer> regions = new HashMap<>();
        regions.put("Region de prueba", 1);
        doReturn(regions).when(facade).getRegions();
        this.mockMvc
                .perform(get("/api/v1/regiones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Map<?, ?> actualObject = objectMapper.readValue(json, Map.class);
                    assertEquals(actualObject, regions);
                });
    }

}
