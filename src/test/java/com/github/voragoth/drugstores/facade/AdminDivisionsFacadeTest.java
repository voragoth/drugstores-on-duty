package com.github.voragoth.drugstores.facade;

import com.github.voragoth.drugstores.facade.impl.AdminDivisionsFacadeImpl;
import com.github.voragoth.drugstores.service.AdminDivisionsProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.simplify4u.sjf4jmock.LoggerMock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Clase de tests de AdminDivisionsFacade
 */
@SpringBootTest
@ActiveProfiles("test")
class AdminDivisionsFacadeTest {

    /**
     * El servicio para hacer stubbing.
     */
    @Mock
    private AdminDivisionsProviderService adminDivisionsProviderService;

    /**
     * La fachada a testear.
     */
    @Spy
    @InjectMocks
    private AdminDivisionsFacade facade = new AdminDivisionsFacadeImpl(adminDivisionsProviderService);

    /**
     * El factory de podam.
     */
    private static PodamFactory factory = new PodamFactoryImpl();

    /**
     * Test unitario getCommunes esperando resultado OK.
     */
    @Test
    @DisplayName("Test unitario getCommunes esperando resultado OK")
    void getCommunesShouldReturnCommunesOK() {

        // objetos necesarios
        Map<String, String> expected = manufactureMap();

        Logger logger = LoggerMock.getLoggerMock(AdminDivisionsFacadeImpl.class);

        // stubbing
        doReturn(expected).when(adminDivisionsProviderService).getCommunes(anyString());
        when(logger.isDebugEnabled()).thenReturn(true);

        //test
        Map<String, String> output = facade.getCommunes("1");

        // assert y verificaciones
        assertEquals(output, expected);
        verify(adminDivisionsProviderService, times(1)).getCommunes(anyString());
    }

    /**
     * Test unitario getCommunes esperando que lance excepcion.
     */
    @Test
    @DisplayName("Test unitario getCommunes esperando que lance excepcion")
    void getCommunesShouldThrowsException() {
        // stubbing
        doReturn(new HashMap<>()).when(adminDivisionsProviderService).getCommunes(anyString());

        // test y assert
        assertThrows(ResponseStatusException.class, () -> facade.getCommunes("1"));

        // verificacion
        verify(adminDivisionsProviderService, times(1)).getCommunes(anyString());
    }

    /**
     * Test unitario getRegions esperando resultado OK.
     */
    @Test
    @DisplayName("Test unitario getRegions esperando resultado OK")
    void getRegionsShouldReturnOK() {
        // objetos necesarios
        Map<String, String> expected = manufactureMap();
        Logger logger = LoggerMock.getLoggerMock(AdminDivisionsFacadeImpl.class);

        // stubbing
        doReturn(expected).when(adminDivisionsProviderService).getRegions();
        when(logger.isDebugEnabled()).thenReturn(true);

        // test
        Map<String, String> output = facade.getRegions();

        //asserts y verificacion
        assertEquals(output, expected);
        verify(adminDivisionsProviderService, times(1)).getRegions();
    }

    /**
     * Test unitario getRegions esperando que lance excepcion
     */
    @Test
    @DisplayName("Test unitario getRegions esperando que lance excepcion")
    void getRegionsShouldThrowsException() {
        // stubbing
        doReturn(new HashMap<>()).when(adminDivisionsProviderService).getRegions();

        // tests y assert
        assertThrows(ResponseStatusException.class, facade::getRegions);

        // verificacion
        verify(adminDivisionsProviderService, times(1)).getRegions();
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
