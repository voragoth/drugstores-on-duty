package com.github.voragoth.drugstores.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Clase de configuracion para los clientes feign que reciben multipart/form-data
 * Incluye un decoder y encoder personalizados.
 * No es una clase marcada con @Configuration, por lo tanto se agrega manualmente a cada cliente feign.
 *
 * @author Manuel Vasquez Cruz.
 */
public class HtmlResponsesFeignConfig {

    /**
     * Creacion del bean de decoder para el cliente feign que lo requiera.
     *
     * @return el decoder para feign.
     */
    @Bean
    public Decoder jsoupDecoder() {
        return (response, type) -> {
            try (InputStream is = response.body().asInputStream()) {
                return Jsoup.parse(is, Charset.defaultCharset().name(), response.request().url());
            }
        };
    }

    /**
     * Creacion del bean de encoder para el cliente feign que lo requiera.
     *
     * @param messageConverters bean existente en el contexto
     * @return el encoder para feign.
     */
    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FormEncoder(new SpringEncoder(messageConverters));
    }


}
