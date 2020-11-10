package com.github.voragoth.drugstores.feign.client;

import com.github.voragoth.drugstores.config.JsonResponseFeignConfig;
import com.github.voragoth.drugstores.constants.ErrorMessagesConstants;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Cliente Feign para obtener las farmacias de turno, retorna text/html siendo JSON, por lo que esta configurado
 * de forma personalizada.
 *
 * @author Manuel Vasquez Cruz.
 */
@FeignClient(name = "drugstores-on-duty", url = "${drugstores.feign.drugstores-on-duty.base-url}",
        configuration = JsonResponseFeignConfig.class,
        fallback = DrugstoreByRegionFeignClient.DrugstoreByRegionFallback.class)
public interface DrugstoreByRegionFeignClient {

    /**
     * Metodo que invoca la api para obtener las farmacias de una region.
     *
     * @param region el id de la region
     * @return las farmacias.
     */
    @GetMapping(value = "${drugstores.feign.drugstores-on-duty.drugstores-by-region-path}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    List<DrugstoreFeignDTO> getDrugstoresByRegion(@RequestParam("id_region") String region);

    /**
     * Metodo que invoca la api para obtener las farmacias de turno del pais.
     *
     * @return las farmacias.
     */
    @GetMapping(value = "${drugstores.feign.drugstores-on-duty.drugstores-on-duty-path}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    List<DrugstoreFeignDTO> getDrugstoresOnDuty();


    /**
     * Clase fallback para el servicio de farmacias
     */
    @Slf4j
    @Component
    class DrugstoreByRegionFallback implements DrugstoreByRegionFeignClient {

        /**
         * {@inheritDoc}
         */
        @Override
        public List<DrugstoreFeignDTO> getDrugstoresByRegion(String region) {
            log.info("metodo fallback farmacias por region");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.DRUGSTORES_BY_REGION_DOWN);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<DrugstoreFeignDTO> getDrugstoresOnDuty() {
            log.info("metodo fallback farmacias de turno");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.DRUGSTORES_ON_DUTY_DOWN);
        }
    }


}
