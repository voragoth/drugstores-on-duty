package com.github.voragoth.drugstores.controller;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.facade.DrugstoresOnDutyFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La clase de controlodor para las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class DrugStoresOnDutyController {

    /**
     * La fachada para logica de las farmacias de turno
     */
    private DrugstoresOnDutyFacade drugstoresOnDutyFacade;

    /**
     * Constructor para enlazar las dependencias del controlador.
     *
     * @param drugstoresOnDutyFacade La fachada con la logica de negocio.
     */
    public DrugStoresOnDutyController(DrugstoresOnDutyFacade drugstoresOnDutyFacade) {
        this.drugstoresOnDutyFacade = drugstoresOnDutyFacade;
    }

    /**
     * Metodo que disponibiliza el endpoint para obtener las farmacias de turno filttrando por local, comuna, y region.
     * Los 3 parametros son opcionales.
     *
     * @param name       el local para filtrar la farmacia, en el endpoint es opcional.
     * @param commune    la comuna para filtrar la farmacia, en el endpoint es opcional.
     * @param region     la region para filtrar la farmacia, en el endpoint es opcional.
     * @param onlyOnDuty filtra si solo se deben retornar las farmacias de turno
     * @return la lista de farmacias filtradas.
     */
    @GetMapping("/v1/farmacias-de-turno")
    public List<Drugstore> getDrugStoresOnDuty(@RequestParam(name = "local", required = false) String name,
                                               @RequestParam(name = "comuna", required = false) String commune,
                                               @RequestParam(name = "region", required = false,
                                                       defaultValue = "${drugstores.default-region:7}") String region,
                                               @RequestParam(name = "solo-de-turno", required = false,
                                                       defaultValue = "false") boolean onlyOnDuty) {
        return drugstoresOnDutyFacade.getDrugStoresOnDuty(name, commune, region, onlyOnDuty);
    }

}
