package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contrato para el servicio que obtiene las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
public interface DrugstoresProviderService {

    /**
     * Metodo con la logica de negocio para obtener las farmacias de turno en la region.
     *
     * @param region  la region para filtrar la farmacia.
     * @return la lista de farmacias de la region.
     */
    List<DrugstoreVO> getDrugStoresOnDuty(@NotNull String region);
}
