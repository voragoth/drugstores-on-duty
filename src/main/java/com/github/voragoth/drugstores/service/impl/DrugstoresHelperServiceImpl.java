package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.constants.ErrorMessagesConstants;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.service.DrugstoresHelperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Componente que contiene metodos de ayuda para la fachada DrugstoresOnDutyFacade.
 *
 * @author Manuel Vasquez Cruz
 */
@Slf4j
@Component
public class DrugstoresHelperServiceImpl implements DrugstoresHelperService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DrugstoreVO> getDrugstoresByRegion(@NotNull String region,
                                                   Future<List<DrugstoreVO>> futureOnDutyDrugstores,
                                                   Future<List<DrugstoreVO>> futureDrugstoresByReg) {

        List<DrugstoreVO> onDutyDrugstores = handleFuture(futureOnDutyDrugstores);
        log.info("{} farmacias de turno sin filtro de region obtenidas", onDutyDrugstores.size());
        if (log.isDebugEnabled()) {
            log.debug("Farmacias de turno sin filtro de region obtenidas: {}", onDutyDrugstores);
        }
        List<DrugstoreVO> drugstoresByReg = handleFuture(futureDrugstoresByReg);
        log.info("{} farmacias sin filtrar turno para la region {}", onDutyDrugstores.size(), region);
        if (log.isDebugEnabled()) {
            log.debug("Farmacias de la region {} sin filtrar turno: {}", region, onDutyDrugstores);
        }

        List<String> onDutyIds = onDutyDrugstores.stream().map(DrugstoreVO::getLocalId).collect(Collectors.toList());

        drugstoresByReg.forEach(d -> putDrugstoreOnDuty(d, onDutyIds));

        return drugstoresByReg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DrugstoreVO> getOnlyOnDutyDrugstores(@NotNull String region,
                                                     Future<List<DrugstoreVO>> futureOnDutyDrugstores) {

        List<DrugstoreVO> onDutyDrugstores = handleFuture(futureOnDutyDrugstores);
        log.info("{} farmacias de turno sin filtro de region obtenidas", onDutyDrugstores.size());
        if (log.isDebugEnabled()) {
            log.debug("Farmacias de turno sin filtro de region obtenidas: {}", onDutyDrugstores);
        }

        onDutyDrugstores = onDutyDrugstores.stream()
                .filter(d -> region.trim().equalsIgnoreCase(StringUtils.trim(d.getRegionId())))
                .map(d -> {
                    d.setOnDuty(true);
                    return d;
                }).collect(Collectors.toList());

        return onDutyDrugstores;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putDrugstoreOnDuty(DrugstoreVO drugstoreVO, List<String> onDutyIds) {
        if (drugstoreVO != null && CollectionUtils.isNotEmpty(onDutyIds)
                && onDutyIds.contains(drugstoreVO.getLocalId().trim())) {
            drugstoreVO.setOnDuty(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Predicate<DrugstoreVO>> createFilter(String name, String commune) {

        Optional<Predicate<DrugstoreVO>> filter = Optional.empty();

        if (StringUtils.isNotBlank(name)) {
            filter = Optional.of(d -> name.trim().equalsIgnoreCase(StringUtils.trim(d.getName())));
        }
        if (StringUtils.isNotBlank(commune) && filter.isPresent()) {
            filter =
                    Optional.of(filter.get().and(d -> StringUtils.normalizeSpace(commune).equalsIgnoreCase(StringUtils.normalizeSpace(d.getCommune()))));
        } else if (StringUtils.isNotBlank(commune)) {
            filter =
                    Optional.of(d -> StringUtils.normalizeSpace(commune).equalsIgnoreCase(StringUtils.normalizeSpace(d.getCommune())));
        }
        return filter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DrugstoreVO> handleFuture(Future<List<DrugstoreVO>> futureDrugstores) {
        List<DrugstoreVO> drugstoreVOS;
        try {
            drugstoreVOS = futureDrugstores.get();
        } catch (InterruptedException e) {
            log.error("Error inesperado en concurrencia", e);
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.UNEXPECTED_CONCURRENCE_ERROR);
        } catch (ExecutionException e) {
            log.error("Error inesperado en concurrencia", e);
            if (e.getCause() instanceof ResponseStatusException) {
                throw (ResponseStatusException) e.getCause();
            }
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.CONCURRENCE_ERROR);
        }
        return drugstoreVOS;
    }
}
