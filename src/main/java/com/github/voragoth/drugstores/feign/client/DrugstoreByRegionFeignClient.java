package com.github.voragoth.drugstores.feign.client;

import com.github.voragoth.drugstores.config.JsonResponseFeignConfig;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Cliente Feign para obtener las farmacias de turno, retorna text/html siendo JSON, por lo que esta configurado
 * de forma personalizada.
 *
 * @author Manuel Vasquez Cruz.
 */
@FeignClient(name = "drugstores-on-duty", url = "${drugstores.feign.drugstores-on-duty.base-url}",
        configuration = JsonResponseFeignConfig.class)
public interface DrugstoreByRegionFeignClient {

    /**
     * Metodo que invoca la api para obtener las farmacias de turno por region.
     *
     * @param region el id de la region
     * @return las farmacias.
     */
    @GetMapping(value = "${drugstores.feign.drugstores-on-duty.drugstores-by-region-path}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    List<DrugstoreFeignDTO> getDrugstoresOnDuty(@RequestParam("id_region") String region);
}
