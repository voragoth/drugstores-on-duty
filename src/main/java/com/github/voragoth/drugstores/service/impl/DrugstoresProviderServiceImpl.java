package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.feign.client.DrugstoreByRegionFeignClient;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementacion del servicio que obtiene y filtra las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
@Slf4j
@Component
public class DrugstoresProviderServiceImpl implements DrugstoresProviderService {

    /**
     * El cliente feign que conecta al servicio de farmacias de turno por region.
     */
    private DrugstoreByRegionFeignClient drugstoreByRegionFeignClient;

    /**
     * El mapper de la aplicacion.
     */
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper;

    /**
     * El constructor necesario para inyectar las dependencias del servicio.
     *
     * @param drugstoreByRegionFeignClient el cliente feign.
     * @param drugstoreOnDutyMapper el mapper de la aplicacion.
     */
    public DrugstoresProviderServiceImpl(DrugstoreByRegionFeignClient drugstoreByRegionFeignClient,
                                         DrugstoreOnDutyMapper drugstoreOnDutyMapper) {
        this.drugstoreByRegionFeignClient = drugstoreByRegionFeignClient;
        this.drugstoreOnDutyMapper = drugstoreOnDutyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable("drugstores")
    public List<DrugstoreVO> getDrugStoresOnDuty(@NotNull String region) {
        List<DrugstoreFeignDTO> drugstoresOnDuty = drugstoreByRegionFeignClient.getDrugstoresOnDuty(region);
        return drugstoresOnDuty.stream().map(
                drugstoreOnDutyMapper::mapDrugstoreFeignDTOToDrugstoreVO)
                .collect(Collectors.toList());
    }
}
