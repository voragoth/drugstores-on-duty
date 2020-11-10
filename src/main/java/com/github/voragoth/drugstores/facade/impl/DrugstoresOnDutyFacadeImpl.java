package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.constants.ErrorMessagesConstants;
import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.facade.DrugstoresOnDutyFacade;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresHelperService;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Implementacion para la fachada de la logica de negocio de farmacias de turno.
 *
 * @author Manuel Vasquez Cruz.
 */
@Slf4j
@Service
public class DrugstoresOnDutyFacadeImpl implements DrugstoresOnDutyFacade {

    /**
     * Servicio que provee de las farmacias por region.
     */
    private DrugstoresProviderService drugstoresProviderService;

    /**
     * Servicio helper para mejorar la legibilidad.
     */
    private DrugstoresHelperService helper;

    /**
     * El mapper de la aplicacion.
     */
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper;


    /**
     * Constructor de la fachada.
     *
     * @param drugstoresProviderService servicio que provee las farmacias por region.
     * @param helper                    componente helper para reducir complejidad.
     * @param drugstoreOnDutyMapper     el mapper de la aplicacion
     */
    public DrugstoresOnDutyFacadeImpl(DrugstoresProviderService drugstoresProviderService,
                                      DrugstoresHelperService helper,
                                      DrugstoreOnDutyMapper drugstoreOnDutyMapper) {
        this.drugstoresProviderService = drugstoresProviderService;
        this.drugstoreOnDutyMapper = drugstoreOnDutyMapper;
        this.helper = helper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drugstore> getDrugStoresOnDuty(String name, String commune, @NotNull String region,
                                               boolean onlyOnDuty) {

        log.info("Obteniendo farmacias de turno sin filtro de region");
        Future<List<DrugstoreVO>> futureOnDutyDrugstores = drugstoresProviderService.getDrugstoresOnDuty();

        List<DrugstoreVO> drugstoresVO;
        if (onlyOnDuty) {
            drugstoresVO = helper.getOnlyOnDutyDrugstores(region, futureOnDutyDrugstores);
        } else {
            log.info("Obteniendo sin filtro de turno de region {}", region);
            Future<List<DrugstoreVO>> futureDrugstoresByReg = drugstoresProviderService.getDrugStoresByRegion(region);
            drugstoresVO = helper.getDrugstoresByRegion(region, futureOnDutyDrugstores, futureDrugstoresByReg);
        }

        if (CollectionUtils.isEmpty(drugstoresVO)) {
            log.error("0 farmacias obtenidas antes de filtrar");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.DRUGSTORES_NOT_FOUND);
        }

        Optional<Predicate<DrugstoreVO>> filter = helper.createFilter(name, commune);
        if (filter.isPresent()) {
            drugstoresVO = drugstoresVO.stream().filter(filter.get()).collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(drugstoresVO)) {
            log.error("0 farmacias obtenidas despues de filtrar");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.DRUGSTORES_NOT_FOUND);
        }

        return drugstoreOnDutyMapper.mapDrugstoreVOListToDrugstoreList(drugstoresVO);
    }


}
