package com.github.voragoth.drugstores.service;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public interface DrugstoresHelperService {

    /**
     * Metodo que retorna la lista de farmacias de turno incluyendo las de no turno, filtrado por region.
     *
     * @param region                 la para logging
     * @param futureOnDutyDrugstores Future con la llamada a farmacias de turno
     * @param futureDrugstoresByReg  Future con la llamada a farmacias por region
     * @return La lista de farmacias.
     */
    List<DrugstoreVO> getDrugstoresByRegion(@NotNull String region, Future<List<DrugstoreVO>> futureOnDutyDrugstores,
                                            Future<List<DrugstoreVO>> futureDrugstoresByReg);

    /**
     * Metodo que retorna la lista de farmacias de turno excluyendo las de no turno, filtrado por region.
     *
     * @param region                 la para logging
     * @param futureOnDutyDrugstores Future con la llamada a farmacias de turno
     * @return la lista de farmacias.
     */
    List<DrugstoreVO> getOnlyOnDutyDrugstores(@NotNull String region, Future<List<DrugstoreVO>> futureOnDutyDrugstores);

    /**
     * Metodo que verifica si la farmacia esta de turno, y deja la bandera de ello en true.
     *
     * @param drugstoreVO la farmacia.
     * @param onDutyIds   los id de farmacias de turno.
     */
    void putDrugstoreOnDuty(DrugstoreVO drugstoreVO, List<String> onDutyIds);

    /**
     * Metodo que crea un filtro en base al nombre de la farmacia y la comuna.
     *
     * @param name    el nombre de la farmacia
     * @param commune el nombre de la comuna
     * @return el filtro compuesto si existe.
     */
    Optional<Predicate<DrugstoreVO>> createFilter(String name, String commune);

    /**
     * Metodo que encapsula las invocaciones de futuros, maneja las excepciones, o retorna el resultado
     *
     * @param futureDrugstores Future con la llamada a farmacias
     * @return el resultado
     * @throws org.springframework.web.server.ResponseStatusException si el hilo falla de alguna forma.
     */
    List<DrugstoreVO> handleFuture(Future<List<DrugstoreVO>> futureDrugstores);
}
