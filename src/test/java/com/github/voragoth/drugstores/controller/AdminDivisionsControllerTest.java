package com.github.voragoth.drugstores.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.voragoth.drugstores.facade.AdminDivisionsFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clase de tests para AdminDivisionsController.
 * <p>
 * Se suprimen warnings de unchecked por uso de podam
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminDivisionsControllerTest {

    /**
     * El objeto MockMvc para ejecutar las llamadas..
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * El mapper de jackson.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * La fachada para los stubbing.
     */
    @MockBean
    private AdminDivisionsFacade facade;

    /**
     * La region por defecto.
     */
    @Value("${drugstores.defaultRegion:7}")
    private String defaultRegion;

    /**
     * El factory de podam.
     */
    private static PodamFactory factory = new PodamFactoryImpl();


    /**
     * Test unitario api/v1/comunas esperando resultado OK.
     *
     * @throws Exception en caso de que MockMvc.perform falle.
     */
    @Test
    @DisplayName("Test unitario api/v1/comunas esperando resultado OK")
    void getCommunesShouldReturnOK() throws Exception {
        // objetos necesarios
        Map<String, String> communes = manufactureMap();

        // stubbing
        doReturn(communes).when(facade).getCommunes(anyString());

        // test y asserts.
        this.mockMvc
                .perform(get("/api/v1/comunas").param("region", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Map<?, ?> actualObject = objectMapper.readValue(json, Map.class);
                    assertEquals(actualObject, communes);
                });

        // verificaciones
        verify(facade, times(1)).getCommunes(anyString());
    }

    /**
     * Test unitario api/v1/comunas esperando resultado OK para region por defecto.
     *
     * @throws Exception en caso de que MockMvc.perform falle.
     */
    @Test
    @DisplayName("Test unitario api/v1/comunas esperando resultado OK para region por defecto")
    void getCommunesShouldReturnOKDefReg() throws Exception {
        // objetos necesarios
        Map<String, String> communes = manufactureMap();

        // stubbing
        doReturn(communes).when(facade).getCommunes(eq(defaultRegion));

        // test y asserts
        this.mockMvc
                .perform(get("/api/v1/comunas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Map<?, ?> actualObject = objectMapper.readValue(json, Map.class);
                    assertEquals(actualObject, communes);
                });

        // verificaciones
        verify(facade).getCommunes(eq(defaultRegion));
    }

    /**
     * Test unitario api/v1/regiones esperando resultado OK.
     *
     * @throws Exception en caso de que MockMvc.perform falle.
     */
    @Test
    @DisplayName("Test unitario api/v1/regiones esperando resultado OK")
    void getRegionsShouldReturnOK() throws Exception {
        // objetos necesarios
        Map<String, String> regions = manufactureMap();

        // stubbing
        doReturn(regions).when(facade).getRegions();

        // test y asserts
        this.mockMvc
                .perform(get("/api/v1/regiones"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    Map<?, ?> actualObject = objectMapper.readValue(json, Map.class);
                    assertEquals(actualObject, regions);
                });

        // verificaciones
        verify(facade).getRegions();
    }

    /**
     * Retorna un Map<String,String> dummy ocultando warning unchecked.
     *
     * @return el map
     */
    private Map<String, String> manufactureMap() {
        @SuppressWarnings("unchecked")
        Map<String, String> map = factory.manufacturePojo(Map.class, String.class, String.class);
        return map;
    }

}
