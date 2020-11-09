package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.constants.ErrorMessagesConstants;
import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.facade.DrugstoresOnDutyFacade;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * El mapper de la aplicacion.
     */
    private DrugstoreOnDutyMapper drugstoreOnDutyMapper;


    /**
     * Constructor de la fachada.
     *
     * @param drugstoresProviderService servicio que provee las farmacias por region.
     * @param drugstoreOnDutyMapper     el mapper de la aplicacion
     */
    public DrugstoresOnDutyFacadeImpl(DrugstoresProviderService drugstoresProviderService,
                                      DrugstoreOnDutyMapper drugstoreOnDutyMapper) {
        this.drugstoresProviderService = drugstoresProviderService;
        this.drugstoreOnDutyMapper = drugstoreOnDutyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Drugstore> getDrugStoresOnDuty(String name, String commune, @NotNull String region) {
        log.info("Obteniendo farmacias");
        List<DrugstoreVO> drugstoresVO = drugstoresProviderService.getDrugStoresOnDuty(region);
        log.info("{} farmacias obtenidas", CollectionUtils.size(drugstoresVO));
        if (log.isDebugEnabled()) {
            log.debug("Farmacias: {}", drugstoresVO);
        }

        if (CollectionUtils.isEmpty(drugstoresVO)) {
            log.error("0 farmacias obtenidas antes de filtrar");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.DRUGSTORES_NOT_FOUND);
        }

        Stream<DrugstoreVO> drugstoresVOStream = drugstoresVO.stream();
        drugstoresVOStream = filterByNameIfNotBlank(name, drugstoresVOStream);
        drugstoresVOStream = filterByCommuneIfNotBlank(commune, drugstoresVOStream);

        List<Drugstore> drugstores = drugstoresVOStream.map(
                drugstoreOnDutyMapper::mapDrugstoreVOToDrugstore).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(drugstores)) {
            log.error("0 farmacias obtenidas despues de filtrar");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessagesConstants.DRUGSTORES_NOT_FOUND);
        }
        return drugstores;
    }

    /**
     * Metodo que filtra por el local si este no esta en blanco.
     *
     * @param name               nombre del local de la farmacia a filtrar
     * @param drugstoresVOStream stream de las farmacias a filtrar
     * @return el stream con el filtro aplicado
     */
    private Stream<DrugstoreVO> filterByNameIfNotBlank(String name, Stream<DrugstoreVO> drugstoresVOStream) {
        if (StringUtils.isNotBlank(name)) {
            log.info("filtrando farmacias por local: {}", name);
            drugstoresVOStream = drugstoresVOStream.filter(
                    d -> name.trim().equalsIgnoreCase(StringUtils.trim(d.getName())));
        }
        return drugstoresVOStream;
    }

    /**
     * Metodo que filtra por la comuna si este no esta en blanco.
     *
     * @param commune            nombre de la comuna de la farmacia a filtrar
     * @param drugstoresVOStream stream de las farmacias a filtrar
     * @return el stream con el filtro aplicado.
     */
    private Stream<DrugstoreVO> filterByCommuneIfNotBlank(String commune, Stream<DrugstoreVO> drugstoresVOStream) {
        if (StringUtils.isNotBlank(commune)) {
            log.info("filtrando farmacias por comuna: {}", commune);
            drugstoresVOStream = drugstoresVOStream.filter(
                    d -> commune.trim().equalsIgnoreCase(StringUtils.trim(d.getCommuneId())));
        }
        return drugstoresVOStream;
    }
}
