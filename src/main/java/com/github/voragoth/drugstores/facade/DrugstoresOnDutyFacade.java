package com.github.voragoth.drugstores.facade;

import com.github.voragoth.drugstores.dto.Drugstore;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contrato para la fachada que orquesta la obtencion de farmacias de turno.
 *
 * @author Manuel Vasquez Cruz.
 */
public interface DrugstoresOnDutyFacade {

    /**
     * Metodo que retorna las farmacias filtradas por local, comuna y/o region.
     *
     * @param name       el local para filtrar la farmacia.
     * @param commune    la comuna para filtrar la farmacia.
     * @param region     la region para filtrar la farmacia, no puede ser null
     * @param onlyOnDuty filtra si solo se deben retornar las farmacias de turno
     * @return la lista de farmacias filtradas.
     */
    List<Drugstore> getDrugStoresOnDuty(String name, String commune, @NotNull String region, boolean onlyOnDuty);

}
