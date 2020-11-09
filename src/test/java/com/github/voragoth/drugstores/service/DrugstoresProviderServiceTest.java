package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.feign.client.DrugstoreByRegionFeignClient;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.impl.DrugstoresProviderServiceImpl;
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
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@SuppressWarnings("unchecked")
class DrugstoresProviderServiceTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    @Mock
    private DrugstoreByRegionFeignClient drugstoreByRegionFeignClient;

    @Spy
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper = Mappers.getMapper(DrugstoreOnDutyMapper.class);

    @Spy
    @InjectMocks
    private DrugstoresProviderService service = new DrugstoresProviderServiceImpl(
            drugstoreByRegionFeignClient, drugstoreOnDutyMapper);

    @Test
    @DisplayName("Test unitario getDrugStoresOnDuty esperando resultado OK")
    void getDrugStoresOnDutyShouldReturnOK() throws Exception {
        // objetos necesarios
        List<DrugstoreFeignDTO> drugstores = factory.manufacturePojo(List.class, DrugstoreFeignDTO.class);
        // NOTA: llama internamente al maper aca las primeras N veces, luego el servicio lo vuelve a hacer
        List<DrugstoreVO> expected = drugstoreOnDutyMapper.mapDrugstoreFeignDTOListToDrugstoreVOList(drugstores);
        int timesToVerify = expected.size() * 2;

        // stubbing
        doReturn(drugstores).when(drugstoreByRegionFeignClient).getDrugstoresOnDuty(anyString());

        // test
        List<DrugstoreVO> actual = service.getDrugStoresOnDuty("test");
        // assert y verificacion
        assertEquals(expected, actual);
        verify(drugstoreByRegionFeignClient, times(1)).getDrugstoresOnDuty(anyString());
        verify(drugstoreOnDutyMapper, times(timesToVerify)).mapDrugstoreFeignDTOToDrugstoreVO(any(DrugstoreFeignDTO.class));
    }

}
