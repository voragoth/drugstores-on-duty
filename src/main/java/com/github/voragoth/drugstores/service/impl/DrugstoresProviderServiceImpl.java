package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.feign.client.DrugstoreByRegionFeignClient;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import com.github.voragoth.drugstores.mapper.DrugstoreOnDutyMapper;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Componente que obtiene y filtra las farmacias de turno de forma asyncrona.
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
     * @param drugstoreOnDutyMapper        el mapper de la aplicacion.
     */
    public DrugstoresProviderServiceImpl(DrugstoreByRegionFeignClient drugstoreByRegionFeignClient,
            DrugstoreOnDutyMapper drugstoreOnDutyMapper) {
        this.drugstoreByRegionFeignClient = drugstoreByRegionFeignClient;
        this.drugstoreOnDutyMapper = drugstoreOnDutyMapper;
    }

    /**
     * Metodo con la logica de negocio para obtener las farmacias de la region.
     *
     * @param region la region para filtrar la farmacia.
     * @return la lista de farmacias de la region.
     */
    @Async
    @Cacheable("drugstores")
    public Future<List<DrugstoreVO>> getDrugStoresByRegion(@NotNull String region) {
        CompletableFuture<List<DrugstoreVO>> future = new CompletableFuture<>();
        List<DrugstoreFeignDTO> drugstoresByRegion = drugstoreByRegionFeignClient.getDrugstoresByRegion(region);
        future.complete(
                drugstoresByRegion.stream().map(drugstoreOnDutyMapper::mapDrugstoreFeignDTOToDrugstoreVO)
                        .collect(Collectors.toList()));

        return future;
    }

    /**
     * Metodo con la logica de negocio para obtener las farmacias en turno del pais.
     *
     * @return la lista de farmacias de la region.
     */
    @Async
    public Future<List<DrugstoreVO>> getDrugstoresOnDuty() {
        CompletableFuture<List<DrugstoreVO>> future = new CompletableFuture<>();
        List<DrugstoreFeignDTO> drugstoresOnDuty = drugstoreByRegionFeignClient.getDrugstoresOnDuty();
        future.complete(
                drugstoresOnDuty.stream().map(drugstoreOnDutyMapper::mapDrugstoreFeignDTOToDrugstoreVO)
                        .collect(Collectors.toList()));
        return future;
    }
}
