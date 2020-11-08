package com.github.voragoth.drugstores.facade;

import com.github.voragoth.drugstores.facade.impl.AdminDivisionsFacadeImpl;
import com.github.voragoth.drugstores.service.AdminDivisionsProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminDivisionsFacadeTest {

    @Mock
    private AdminDivisionsProviderService adminDivisionsProviderService;

    @Spy
    @InjectMocks
    private AdminDivisionsFacade facade = new AdminDivisionsFacadeImpl(adminDivisionsProviderService);

    @Test
    @DisplayName("Test unitario getCommunes esperando resultado OK")
    void getCommunesShouldReturnCommunesOK() throws Exception {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Comuna de test", 1);
        doReturn(expected).when(adminDivisionsProviderService).getCommunes(any(Byte.class));
        Map<String, Integer> output = facade.getCommunes((byte) 1);
        assertEquals(output, expected);
        verify(adminDivisionsProviderService, times(1)).getCommunes(any(Byte.class));
    }

    @Test
    @DisplayName("Test unitario getCommunes esperando que lance excepcion")
    void getCommunesShouldThrowsException() throws Exception {
        doReturn(new HashMap<>()).when(adminDivisionsProviderService).getCommunes(any(Byte.class));
        assertThrows(ResponseStatusException.class, () -> facade.getCommunes((byte) 1));
        verify(adminDivisionsProviderService, times(1)).getCommunes(any(Byte.class));
    }

    @Test
    @DisplayName("Test unitario getRegions esperando resultado OK")
    void getCommunesShouldReturnRegionsOK() throws Exception {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("Region de test", 1);
        doReturn(expected).when(adminDivisionsProviderService).getRegions();
        Map<String, Integer> output = facade.getRegions();
        assertEquals(output, expected);
        verify(adminDivisionsProviderService, times(1)).getRegions();
    }

    @Test
    @DisplayName("Test unitario getRegions esperando que lance excepcion")
    void getRegionsShouldThrowsException() throws Exception {
        doReturn(new HashMap<>()).when(adminDivisionsProviderService).getRegions();
        assertThrows(ResponseStatusException.class, facade::getRegions);
        verify(adminDivisionsProviderService, times(1)).getRegions();
    }

}
