package com.github.voragoth.drugstores.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonResponseFeignConfigTest {

    @Test
    void jsonDecoderDecodeShouldBeOK() throws Exception {
        assertNotNull(new JsonResponseFeignConfig().feignDecoder());
    }

    @Test
    void mappingJackson2HttpMessageConverterShouldBeOK() throws Exception {
        JsonResponseFeignConfig config = new JsonResponseFeignConfig();
        MappingJackson2HttpMessageConverter output = ReflectionTestUtils.invokeMethod(config,
                "mappingJackson2HttpMessageConverter");
        assertNotNull(output);

    }
}
