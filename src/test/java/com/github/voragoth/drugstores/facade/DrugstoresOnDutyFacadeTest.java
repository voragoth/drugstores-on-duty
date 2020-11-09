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

@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings("unchecked")
class DrugstoresOnDutyFacadeTest {

    private static PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private DrugstoresProviderService drugstoresProviderService;

    @Spy
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper = Mappers.getMapper(DrugstoreOnDutyMapper.class);

    @Spy
    @InjectMocks
    private DrugstoresOnDutyFacade facade = new DrugstoresOnDutyFacadeImpl(
            drugstoresProviderService, drugstoreOnDutyMapper);

    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK sin filtrar farmacias")
    void getDrugStoresOnDutyShouldReturnOK1() throws Exception {
        // objetos necesarios
        List<DrugstoreVO> drugstores = factory.manufacturePojo(List.class, DrugstoreVO.class);
        // *1) llama internamente a mapDrugstoreVOToDrugstore las primeras N veces, luego el servicio lo vuelve a hacer
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

    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK filtrando por local")
    void getDrugStoresOnDutyShouldReturnOK2() throws Exception {
        // objetos necesarios
        List<DrugstoreVO> drugstores = factory.manufacturePojo(List.class, DrugstoreVO.class);
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

    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK filtrando por comuna")
    void getDrugStoresOnDutyShouldReturnOK3() throws Exception {
        // objetos necesarios
        List<DrugstoreVO> drugstores = factory.manufacturePojo(List.class, DrugstoreVO.class);
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

}
