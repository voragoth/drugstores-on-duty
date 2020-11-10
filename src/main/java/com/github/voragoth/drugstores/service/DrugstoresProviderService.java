package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import org.springframework.scheduling.annotation.Async;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Contrato para el servicio que obtiene las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
public interface DrugstoresProviderService {

    /**
     * Metodo con la logica de negocio para obtener las farmacias en la region s
     *
     * @param region  la region para filtrar la farmacia.
     * @return la lista de farmacias de la region.
     */
    @Async
    Future<List<DrugstoreVO>> getDrugStoresByRegion(@NotNull String region);


    /**
     * Metodo con la logica de negocio para obtener las farmacias en turno del pais.
     *
     * @return la lista de farmacias de la region.
     */
    @Async
    Future<List<DrugstoreVO>> getDrugstoresOnDuty();
}
