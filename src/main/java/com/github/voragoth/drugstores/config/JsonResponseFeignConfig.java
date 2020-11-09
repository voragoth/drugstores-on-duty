package com.github.voragoth.drugstores.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

public class JsonResponseFeignConfig {

    @Bean
    public Decoder feignDecoder() {
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(mappingJackson2HttpMessageConverter());
        return new SpringDecoder(objectFactory);
    }


    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()  {
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
