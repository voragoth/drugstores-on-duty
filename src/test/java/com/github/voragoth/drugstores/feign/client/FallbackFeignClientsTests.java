package com.github.voragoth.drugstores.feign.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clase de test para metodos fallback de los clientes feign
 */
class FallbackFeignClientsTests {

    /**
     * clase fallback para AdminDivisionsFeignClient
     */
    AdminDivisionsFeignClient.AdminDivisionsFeignClientFallback adminDivisionsFeignClientFallback =
            new AdminDivisionsFeignClient.AdminDivisionsFeignClientFallback();

    /**
     * clase de fallback para DrugstoreByRegionFeignClient
     */
    DrugstoreByRegionFeignClient.DrugstoreByRegionFallback drugstoreByRegionFallback =
            new DrugstoreByRegionFeignClient.DrugstoreByRegionFallback();

    /**
     * Test unitario de todos los metodos fallback de los clientes feign
     */
    @Test
    @DisplayName("Test unitario de todos los metodos fallback de los clientes feign")
    void testAllFallbackMethods() {
        assertThrows(ResponseStatusException.class, () -> adminDivisionsFeignClientFallback.getCommunes(null));
        assertThrows(ResponseStatusException.class, () -> adminDivisionsFeignClientFallback.getRegions());
        assertThrows(ResponseStatusException.class, () -> drugstoreByRegionFallback.getDrugstoresByRegion("test"));
        assertThrows(ResponseStatusException.class, () -> drugstoreByRegionFallback.getDrugstoresOnDuty());
    }
}
