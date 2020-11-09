package com.github.voragoth.drugstores.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe de test para JsonResponseFeignConfig
 */
class JsonResponseFeignConfigTest {

    /**
     * Test unitario para creacion de decoder en JsonResponseFeignConfig
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Test unitario para creacion de decoder en JsonResponseFeignConfig")
    void jsonDecoderDecodeShouldBeOK() throws Exception {
        //asserts
        assertNotNull(new JsonResponseFeignConfig().feignDecoder());
    }

    /**
     * Test unitario para metodo mappingJackson2HttpMessageConverter en JsonResponseFeignConfig.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("Test unitario para metodo mappingJackson2HttpMessageConverter en JsonResponseFeignConfig")
    void mappingJackson2HttpMessageConverterShouldBeOK() throws Exception {
        // objetos necesarios
        JsonResponseFeignConfig config = new JsonResponseFeignConfig();

        // test
        MappingJackson2HttpMessageConverter output = ReflectionTestUtils.invokeMethod(config,
                "mappingJackson2HttpMessageConverter");

        // asserts
        assertNotNull(output);

    }
}
