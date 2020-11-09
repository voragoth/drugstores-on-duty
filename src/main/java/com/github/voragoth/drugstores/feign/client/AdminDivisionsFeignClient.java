package com.github.voragoth.drugstores.feign.client;

import com.github.voragoth.drugstores.config.HtmlResponsesFeignConfig;
import org.jsoup.nodes.Document;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * Cliente Feign para obtener regiones y comunas desde una api no rest.
 *
 * @author Manuel Vasquez Cruz.
 */
@FeignClient(name = "admin-divisions", url = "${drugstores.feign.admin-divisions.base-url}",
        configuration = HtmlResponsesFeignConfig.class)
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
}