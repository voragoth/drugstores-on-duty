package com.github.voragoth.drugstores.facade;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.facade.impl.DrugstoresOnDutyFacadeImpl;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Clase de test para DrugstoresOnDutyFacade.
 */
@SpringBootTest
@ActiveProfiles("test")
class DrugstoresOnDutyFacadeTest {

    /**
     * El servicio para hacer stubbing.
     */
    @Mock
    private DrugstoresProviderService drugstoresProviderService;

    /**
     * El mapper.
     */
    @Spy
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper = Mappers.getMapper(DrugstoreOnDutyMapper.class);

    /**
     * La fachada a testear.
     */
    @Spy
    @InjectMocks
    private DrugstoresOnDutyFacade facade = new DrugstoresOnDutyFacadeImpl(
            drugstoresProviderService, drugstoreOnDutyMapper);

    /**
     * El factory de podam.
     */
    private static PodamFactory factory = new PodamFactoryImpl();

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado OK sin filtrar farmacias
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK sin filtrar farmacias")
    void getDrugStoresOnDutyShouldReturnOK1() {
        // objetos necesarios
        List<DrugstoreVO> drugstores = manufactureList();
        // NOTA: llama internamente al maper aca las primeras N veces, luego el servicio lo vuelve a hacer
        List<Drugstore> expected = drugstoreOnDutyMapper.mapDrugstoreVOListToDrugstoreList(drugstores);
        int expectedTimes = expected.size() * 2;

        // stubbing
        doReturn(drugstores).when(drugstoresProviderService).getDrugStoresOnDuty(any());

        // test
        List<Drugstore> actual = facade.getDrugStoresOnDuty(null, null, "TEST");

        // assert y verificaciones
        assertEquals(expected, actual);
        verify(drugstoresProviderService, times(1)).getDrugStoresOnDuty(anyString());
        verify(drugstoreOnDutyMapper, times(expectedTimes)).mapDrugstoreVOToDrugstore(any(DrugstoreVO.class));
    }

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado OK filtrando por local
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK filtrando por local")
    void getDrugStoresOnDutyShouldReturnOK2() {
        // objetos necesarios
        List<DrugstoreVO> drugstores = manufactureList();
        String local = "Local de prueba";
        drugstores.get(0).setName(local);
        Drugstore expected = drugstoreOnDutyMapper.mapDrugstoreVOToDrugstore(drugstores.get(0));

        // stubbing
        doReturn(drugstores).when(drugstoresProviderService).getDrugStoresOnDuty(anyString());

        // test
        List<Drugstore> actual = facade.getDrugStoresOnDuty(local, null, "TEST");

        // assert y verificaciones
        assertEquals(expected, actual.get(0));
        verify(drugstoresProviderService, times(1)).getDrugStoresOnDuty(anyString());
        verify(drugstoreOnDutyMapper, times(2)).mapDrugstoreVOToDrugstore(any(DrugstoreVO.class));
    }

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado OK filtrando por comuna
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK filtrando por comuna")
    void getDrugStoresOnDutyShouldReturnOK3() {
        // objetos necesarios
        List<DrugstoreVO> drugstores = manufactureList();
        String commune = "2";
        drugstores.get(0).setCommuneId(commune);
        Drugstore expected = drugstoreOnDutyMapper.mapDrugstoreVOToDrugstore(drugstores.get(0));

        // stubbing
        doReturn(drugstores).when(drugstoresProviderService).getDrugStoresOnDuty(anyString());

        // test
        List<Drugstore> actual = facade.getDrugStoresOnDuty(null, commune, "TEST");

        // assert y verificaciones
        assertEquals(expected, actual.get(0));
        verify(drugstoresProviderService, times(1)).getDrugStoresOnDuty(anyString());
        verify(drugstoreOnDutyMapper, times(2)).mapDrugstoreVOToDrugstore(any(DrugstoreVO.class));
    }

    /**
     * Metodo para retornar una lista DrugstoreVO con podam
     *
     * @return la lista generada por podam.
     */
    private List<DrugstoreVO> manufactureList() {
        @SuppressWarnings("unchecked")
        List<DrugstoreVO> drugstores = factory.manufacturePojo(List.class, DrugstoreVO.class);
        return drugstores;
    }

}
