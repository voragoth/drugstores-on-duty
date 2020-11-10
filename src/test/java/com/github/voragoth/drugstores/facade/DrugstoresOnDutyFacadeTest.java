package com.github.voragoth.drugstores.facade;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.facade.impl.DrugstoresOnDutyFacadeImpl;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresHelperService;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
     * El helper para hacer stubbing.
     */
    @Mock
    private DrugstoresHelperService helper;

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
    private DrugstoresOnDutyFacade facade = new DrugstoresOnDutyFacadeImpl(drugstoresProviderService, helper,
            drugstoreOnDutyMapper);

    /**
     * El factory de podam.
     */
    private static PodamFactory factory = new PodamFactoryImpl();

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado OK solo turno
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK solo turno")
    void getDrugStoresOnDutyShouldReturnOK1() {
        // objetos necesarios
        List<DrugstoreVO> drugstores = manufactureList();
        List<Drugstore> expected = drugstoreOnDutyMapper.mapDrugstoreVOListToDrugstoreList(drugstores);

        // stubbing
        doReturn(new AsyncResult<DrugstoreVO>(new DrugstoreVO())).when(drugstoresProviderService).getDrugstoresOnDuty();
        doReturn(drugstores).when(helper).getOnlyOnDutyDrugstores(anyString(), any());
        doReturn(Optional.empty()).when(helper).createFilter(anyString(), anyString());

        // test
        List<Drugstore> actual = facade.getDrugStoresOnDuty("test", "test", "TEST", true);

        // assert y verificaciones
        assertEquals(expected, actual);
        verify(drugstoresProviderService).getDrugstoresOnDuty();
        verify(helper).getOnlyOnDutyDrugstores(anyString(), any());
        verify(helper).createFilter(anyString(), anyString());
    }

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado OK incluyendo las no de turno
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK incluyendo las no de turno")
    void getDrugStoresOnDutyShouldReturnOK2() {
        // objetos necesarios
        List<DrugstoreVO> drugstores = manufactureList();
        List<Drugstore> expected = drugstoreOnDutyMapper.mapDrugstoreVOListToDrugstoreList(drugstores);

        // stubbing
        doReturn(new AsyncResult<DrugstoreVO>(new DrugstoreVO())).when(drugstoresProviderService).getDrugstoresOnDuty();
        doReturn(new AsyncResult<DrugstoreVO>(new DrugstoreVO())).when(drugstoresProviderService).getDrugStoresByRegion(anyString());
        doReturn(drugstores).when(helper).getDrugstoresByRegion(anyString(), any(), any());
        doReturn(Optional.empty()).when(helper).createFilter(anyString(), anyString());

        // test
        List<Drugstore> actual = facade.getDrugStoresOnDuty("test", "test", "TEST", false);

        // assert y verificaciones
        assertEquals(expected, actual);
        verify(drugstoresProviderService).getDrugstoresOnDuty();
        verify(drugstoresProviderService).getDrugStoresByRegion(anyString());
        verify(helper).getDrugstoresByRegion(anyString(), any(), any());
        verify(helper).createFilter(anyString(), anyString());
    }

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado KO sin farmacias obtenidas antes de filtrar
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado KO sin farmacias obtenidas antes de filtrar")
    void getDrugStoresOnDutyShouldThrowException1() {
        // stubbing
        doReturn(new AsyncResult<DrugstoreVO>(new DrugstoreVO())).when(drugstoresProviderService).getDrugstoresOnDuty();
        doReturn(new ArrayList<>()).when(helper).getOnlyOnDutyDrugstores(anyString(), any());

        // test y assert
        assertThrows(ResponseStatusException.class, () -> facade.getDrugStoresOnDuty("test", "test", "TEST", true));

        // assert y verificaciones
        verify(drugstoresProviderService).getDrugstoresOnDuty();
        verify(helper).getOnlyOnDutyDrugstores(anyString(), any());
    }

    /**
     * Test unitario getDrugStoresOnDuty esperando resultado KO sin farmacias obtenidas despues de filtrar
     */
    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado KO sin farmacias obtenidas despues de filtrar")
    void getDrugStoresOnDutyShouldThrowException2() {
        // objetos necesarios
        List<DrugstoreVO> drugstores = manufactureList();
        Predicate<DrugstoreVO> filter = (DrugstoreVO d) -> d.getLatitude().equals("match imposible");

        // stubbing
        doReturn(new AsyncResult<DrugstoreVO>(new DrugstoreVO())).when(drugstoresProviderService).getDrugstoresOnDuty();
        doReturn(drugstores).when(helper).getOnlyOnDutyDrugstores(anyString(), any());
        doReturn(Optional.of(filter)).when(helper).createFilter(anyString(), anyString());

        // test y assert
        assertThrows(ResponseStatusException.class, () -> facade.getDrugStoresOnDuty("test", "test", "TEST", true));

        // assert y verificaciones
        verify(drugstoresProviderService).getDrugstoresOnDuty();
        verify(helper).getOnlyOnDutyDrugstores(anyString(), any());
        verify(helper).createFilter(anyString(), anyString());
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
