package com.github.voragoth.drugstores.feign.client;

import com.github.voragoth.drugstores.config.HtmlResponsesFeignConfig;
import com.github.voragoth.drugstores.constants.ErrorMessagesConstants;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Cliente Feign para obtener regiones y comunas desde una api no rest.
 *
 * @author Manuel Vasquez Cruz.
 */
@FeignClient(name = "admin-divisions", url = "${drugstores.feign.admin-divisions.base-url}",
        configuration = HtmlResponsesFeignConfig.class,
        fallback = AdminDivisionsFeignClient.AdminDivisionsFeignClientFallback.class)
public interface AdminDivisionsFeignClient {


    /**
     * Metodo que invoca al servicio que retorna las regiones del pais.
     *
     * @return el documento html con las regiones
     */
    @PostMapping(value = "${drugstores.feign.admin-divisions.regions-path}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.TEXT_HTML_VALUE})
    Document getRegions();

    /**
     * Metodo que invoca al servicio que retorna las comunas de una region especifica.
     *
     * @param formParams la entrada multipart/form-data debe tener el valor 'reg_id'.
     * @return las comunas.
     */
    @PostMapping(value = "${drugstores.feign.admin-divisions.communes-path}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.TEXT_HTML_VALUE})
    Document getCommunes(Map<String, ?> formParams);

    /**
     * Clase fallback para el servicio de comunas y regiones
     */
    @Slf4j
    @Component
    class AdminDivisionsFeignClientFallback implements AdminDivisionsFeignClient {

        /**
         * {@inheritDoc}
         */
        @Override
        public Document getRegions() {
            log.info("metodo fallback obtener regiones");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.REGIONS_DOWN);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Document getCommunes(Map<String, ?> formParams) {
            log.info("metodo fallback obtener comunas");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessagesConstants.COMMUNES_DOWN);
        }
    }
}