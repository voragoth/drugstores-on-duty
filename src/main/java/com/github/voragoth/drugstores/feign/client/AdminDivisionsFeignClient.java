package com.github.voragoth.drugstores.feign.client;

import com.github.voragoth.drugstores.config.HtmlResponsesFeignConfig;
import feign.Headers;
import org.jsoup.nodes.Document;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "admin-divisions", url = "${drugstores.feign.admin-divisions.base-url}",
        configuration = HtmlResponsesFeignConfig.class)
public interface AdminDivisionsFeignClient {

    @PostMapping(value = "${drugstores.feign.admin-divisions.regions-path}")
    @Headers("Content-Type: multipart/form-data")
    Document getRegions();

    @PostMapping(value = "${drugstores.feign.admin-divisions.communes-path}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Headers("Content-Type: multipart/form-data")
    Document getCommunes(Map<String, ?> formParams);
}