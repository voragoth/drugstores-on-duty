package com.github.voragoth.drugstores.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.facade.DrugstoresOnDutyFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Clase de tests para DrugStoresOnDutyController.
 * <p>
 * Se suprimen warnings de unchecked por uso de podam
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DrugStoresOnDutyControllerTest {

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
    private DrugstoresOnDutyFacade facade;

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
    void getDrugStoresOnDutyReturnOK() throws Exception {
        // objetos necesarios
        List<Drugstore> drugstores = manufactureList();
        doReturn(drugstores).when(facade).getDrugStoresOnDuty(anyString(), anyString(), anyString());

        // test y asserts
        this.mockMvc
                .perform(get("/api/v1/farmacias-de-turno")
                        .param("local", "1")
                        .param("comuna", "1")
                        .param("region", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    List<Drugstore> actualObject = objectMapper.readValue(json, new TypeReference<List<Drugstore>>() {
                    });
                    assertEquals(actualObject, drugstores);
                });
        // verificaciones
        verify(facade, times(1)).getDrugStoresOnDuty(anyString(), anyString(), anyString());
    }

    /**
     * Crea una lista dummy con podam y encapsula el waring de unchecked.
     *
     * @return la lista de Drugstore
     */
    private List<Drugstore> manufactureList() {
        @SuppressWarnings("unchecked")
        List<Drugstore> drugstores = factory.manufacturePojo(List.class, Drugstore.class);
        return drugstores;
    }

}
