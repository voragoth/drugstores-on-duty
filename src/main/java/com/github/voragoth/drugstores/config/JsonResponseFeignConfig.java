package com.github.voragoth.drugstores.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de configuracion para los clientes feign que retornan el mediatype text/html siendo JSON.
 * Incluye un decoder y encoder personalizados.
 * No es una clase marcada con @Configuration, por lo tanto se agrega manualmente a cada cliente feign.
 *
 * @author Manuel Vasquez Cruz.
 */
public class JsonResponseFeignConfig {

    /**
     * Creacion del bean de decoder para el cliente feign que lo requiera.
     *
     * @return el decoder para feign.
     */
    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(() -> new HttpMessageConverters(mappingJackson2HttpMessageConverter()));
    }


    /**
     * Metodo que retorna una version personalizada de jacksonConverter para mediatype text/html.
     *
     * @return la version personalizada de jackson converter.
     */
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.setObjectMapper(objectMapper);
        List<MediaType> jacksonTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        jacksonTypes.add(MediaType.TEXT_HTML);
        converter.setSupportedMediaTypes(jacksonTypes);
        return converter;
    }
}
