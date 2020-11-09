package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.feign.client.AdminDivisionsFeignClient;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.impl.AdminDivisionsProviderServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Clase de test para AdminDivisionsProviderService
 */
@SpringBootTest
@ActiveProfiles("test")
class AdminDivisionsProviderServiceTest {

    /**
     * Cliente feign para stubbing.
     */
    @Mock
    private AdminDivisionsFeignClient adminDivisionsFeignClient;

    /**
     * El mapper.
     */
    @Spy
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper = Mappers.getMapper(DrugstoreOnDutyMapper.class);

    /**
     * El servicio a testear.
     */
    @Spy
    @InjectMocks
    private AdminDivisionsProviderService service = new AdminDivisionsProviderServiceImpl(adminDivisionsFeignClient,
            drugstoreOnDutyMapper);


    /**
     * Test unitario getRegions esperando resultado OK
     */
    @Test
    @DisplayName("Test unitario getRegions esperando resultado OK")
    void getRegionsShouldReturnOK() {
        // objetos necesarios
        String html = "<option value='0' selected>Elija Comuna</option><option value='1'>TEST</option>";
        Document doc = Jsoup.parse(html);
        List<Element> elements = doc.select("option").stream().filter(
                el -> !el.hasAttr("selected")).collect(Collectors.toList());
        Map<String, String> expected = drugstoreOnDutyMapper.mapElementListToMap(elements);
        drugstoreOnDutyMapper.mapElementListToMap(null);

        // stubbing
        doReturn(doc).when(adminDivisionsFeignClient).getRegions();

        //test
        Map<String, String> regions = service.getRegions();

        //assert y verificacion
        assertEquals(expected, regions);
        verify(adminDivisionsFeignClient, times(1)).getRegions();
        verify(drugstoreOnDutyMapper, times(2)).mapElementListToMap(anyList());
    }


    /**
     * Test unitario getCommunes esperando resultado OK
     */
    @Test
    @DisplayName("Test unitario getCommunes esperando resultado OK")
    void getCommunessShouldReturnOK() {
        // objetos necesarios
        String html = "<option value='0' selected>Elija Region</option><option value='1'>TEST</option>";
        Document doc = Jsoup.parse(html);
        List<Element> elements = doc.select("option").stream().filter(
                el -> !el.hasAttr("selected")).collect(Collectors.toList());
        Map<String, String> expected = drugstoreOnDutyMapper.mapElementListToMap(elements);

        // stubbing
        doReturn(doc).when(adminDivisionsFeignClient).getCommunes(anyMap());

        // test
        Map<String, String> communes = service.getCommunes("1");

        // assert y verificacion
        assertEquals(expected, communes);
        verify(adminDivisionsFeignClient, times(1)).getCommunes(anyMap());
        verify(drugstoreOnDutyMapper, times(2)).mapElementListToMap(anyList());
    }
}
