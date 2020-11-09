package com.github.voragoth.drugstores.feign.client;

import com.github.voragoth.drugstores.config.JsonResponseFeignConfig;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "drugstores-on-duty", url = "${drugstores.feign.drugstores-on-duty.base-url}",
        configuration = JsonResponseFeignConfig.class)
public interface DrugstoreByRegionFeignClient {

    @GetMapping(value = "${drugstores.feign.drugstores-on-duty.drugstores-by-region-path}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<DrugstoreFeignDTO> getDrugstoresOnDuty(@RequestParam("id_region") String region);
}
