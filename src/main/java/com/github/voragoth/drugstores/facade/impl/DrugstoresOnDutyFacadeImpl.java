package com.github.voragoth.drugstores.facade.impl;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.facade.DrugstoresOnDutyFacade;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
        List<DrugstoreVO> drugstoresVO = drugstoresProviderService.getDrugStoresOnDuty(region);
        log.info("Respuesta farmacias de turno: {}", drugstoresVO);
        Stream<DrugstoreVO> drugstoresVOStream = drugstoresVO.stream();

        // filtrado por local
        if (StringUtils.isNotEmpty(name)) {
            drugstoresVOStream = drugstoresVOStream.filter(
                    d -> name.trim().equalsIgnoreCase(StringUtils.trim(d.getName())));
        }

        // filtrado por comuna
        if (StringUtils.isNotEmpty(commune)) {
            drugstoresVOStream = drugstoresVOStream.filter(
                    d -> commune.trim().equalsIgnoreCase(StringUtils.trim(d.getCommuneId())));
        }
        return drugstoresVOStream.map(drugstoreOnDutyMapper::mapDrugstoreVOToDrugstore).collect(Collectors.toList());
    }
}
