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

@SpringBootTest
@ActiveProfiles("test")
class AdminDivisionsProviderServiceTest {

    @Mock
    private AdminDivisionsFeignClient adminDivisionsFeignClient;

    @Spy
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper = Mappers.getMapper(DrugstoreOnDutyMapper.class);

    @Spy
    @InjectMocks
    private AdminDivisionsProviderService service = new AdminDivisionsProviderServiceImpl(adminDivisionsFeignClient,
            drugstoreOnDutyMapper);


    @Test
    @DisplayName("Test unitario getRegions esperando resultado OK")
    void getRegionsShouldReturnOK() throws Exception {
        // objetos necesarios
        String html = "<option value='0' selected>Elija Comuna</option><option value='1'>TEST</option>";
        Document doc = Jsoup.parse(html);
        List<Element> elements = doc.select("option").stream().filter(
                el -> !el.hasAttr("selected")).collect(Collectors.toList());
        Map<String, Integer> expected = drugstoreOnDutyMapper.mapElementListToMap(elements);

        // stubbing
        doReturn(doc).when(adminDivisionsFeignClient).getRegions();

        //test
        Map<String, Integer> regions = service.getRegions();

        //assert y verificacion
        assertEquals(expected, regions);
        verify(adminDivisionsFeignClient, times(1)).getRegions();
        verify(drugstoreOnDutyMapper, times(2)).mapElementListToMap(anyList());
    }


    @Test
    @DisplayName("Test unitario getCommunes esperando resultado OK")
    void getCommunessShouldReturnOK() throws Exception {
        // objetos necesarios
        String html = "<option value='0' selected>Elija Region</option><option value='1'>TEST</option>";
        Document doc = Jsoup.parse(html);
        List<Element> elements = doc.select("option").stream().filter(
                el -> !el.hasAttr("selected")).collect(Collectors.toList());
        Map<String, Integer> expected = drugstoreOnDutyMapper.mapElementListToMap(elements);

        // stubbing
        doReturn(doc).when(adminDivisionsFeignClient).getCommunes(anyMap());

        // test
        Map<String, Integer> communes = service.getCommunes(Byte.valueOf("1"));

        // assert y verificacion
        assertEquals(expected, communes);
        verify(adminDivisionsFeignClient, times(1)).getCommunes(anyMap());
        verify(drugstoreOnDutyMapper, times(2)).mapElementListToMap(anyList());
    }
}
