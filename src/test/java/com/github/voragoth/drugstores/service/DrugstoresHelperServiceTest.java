package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.service.impl.DrugstoresHelperServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.simplify4u.sjf4jmock.LoggerMock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Clase de test para DrugstoresHelperService
 */
@SpringBootTest
@ActiveProfiles("test")
class DrugstoresHelperServiceTest {

    @Spy
    @InjectMocks
    private DrugstoresHelperService service = new DrugstoresHelperServiceImpl();

    /**
     * El factory de podam.
     */
    private static PodamFactory factory = new PodamFactoryImpl();

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Test unitario para getDrugstoresByRegion con debug enabled y finalizando ok")
    void getDrugstoresByRegionShouldBeOk1(boolean debugEnabled) {
        // objetos necesarios
        Future<List<DrugstoreVO>> futureOnDutyDrugstores = new AsyncResult<>(manufactureList());
        Future<List<DrugstoreVO>> futureDrugstoresByReg = new AsyncResult<>(manufactureList());
        Logger logger = LoggerMock.getLoggerMock(DrugstoresHelperServiceImpl.class);

        //stubbing
        when(logger.isDebugEnabled()).thenReturn(debugEnabled);

        //test
        List<DrugstoreVO> output = service.getDrugstoresByRegion("test", futureOnDutyDrugstores, futureDrugstoresByReg);

        //asserts
        assertNotNull(output);
        assertTrue(CollectionUtils.isNotEmpty(output));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Test unitario para getOnlyOnDutyDrugstores sin debug enabled y finalizando ok")
    void getOnlyOnDutyDrugstoresShouldBeOK1(boolean debugEnabled) {
        // objetos necesarios
        List<DrugstoreVO> drugstoreVOS = manufactureList();
        DrugstoreVO expectedItem = factory.manufacturePojo(DrugstoreVO.class);
        expectedItem.setRegionId("test");
        drugstoreVOS.add(expectedItem);
        List<DrugstoreVO> expected = new ArrayList<>();
        expected.add(expectedItem);
        Future<List<DrugstoreVO>> futureOnDutyDrugstores = new AsyncResult<>(drugstoreVOS);
        Logger logger = LoggerMock.getLoggerMock(DrugstoresHelperServiceImpl.class);

        //stubbing
        when(logger.isDebugEnabled()).thenReturn(debugEnabled);

        //test
        List<DrugstoreVO> actual = service.getOnlyOnDutyDrugstores("test", futureOnDutyDrugstores);

        //asserts
        assertEquals(expected, actual);
    }

    /**
     * Test unitario para putDrugstoreOnDuty en todas sus casuisticas
     */
    @Test
    @DisplayName("Test unitario para putDrugstoreOnDuty en todas sus casuisticas")
    void putDrugstoreOnDutyShouldBeOk() {
        // objetos necesarios
        DrugstoreVO drugstoreVO = factory.manufacturePojo(DrugstoreVO.class);
        List<String> onDutyids = new ArrayList<>();
        drugstoreVO.setOnDuty(false);
        onDutyids.add(drugstoreVO.getLocalId());

        // tests
        service.putDrugstoreOnDuty(drugstoreVO, onDutyids);
        service.putDrugstoreOnDuty(null, onDutyids);
        service.putDrugstoreOnDuty(drugstoreVO, new ArrayList<>());

        // assert
        assertTrue(drugstoreVO.isOnDuty());

    }

    /**
     * Test unitario para createFilter en todas sus casuisticas
     */
    @Test
    @DisplayName("Test unitario para createFilter en todas sus casuisticas")
    void createFilterShouldBeOk() {
        // tests y assert
        assertTrue(service.createFilter("test", "test").isPresent());
        assertTrue(service.createFilter(null, "test").isPresent());
        assertTrue(service.createFilter("test", null).isPresent());
        assertFalse(service.createFilter(null, null).isPresent());

    }

    /**
     *
     * Test unitario para handleFuture lanzando excepciones
     *
     * @throws Exception para cumplir get del future.
     */
    @Test
    @DisplayName("Test unitario para handleFuture lanzando excepciones")
    void handleFutureShouldThrowExceptions() throws Exception{
        // objetos necesarios
        @SuppressWarnings("unchecked")
        Future<List<DrugstoreVO>> future1 = mock(Future.class);
        @SuppressWarnings("unchecked")
        Future<List<DrugstoreVO>> future2 = mock(Future.class);
        @SuppressWarnings("unchecked")
        Future<List<DrugstoreVO>> future3 = mock(Future.class);
        ExecutionException ee = mock(ExecutionException.class);

        // stubbing
        doThrow(InterruptedException.class).when(future1).get();
        doThrow(ExecutionException.class).when(future2).get();
        doReturn(new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "test")).when(ee).getCause();
        doThrow(ee).when(future3).get();
        // tests y assert
        assertThrows(ResponseStatusException.class, () -> service.handleFuture(future1));
        assertThrows(ResponseStatusException.class, () -> service.handleFuture(future2));
        assertThrows(ResponseStatusException.class, () -> service.handleFuture(future3));

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
