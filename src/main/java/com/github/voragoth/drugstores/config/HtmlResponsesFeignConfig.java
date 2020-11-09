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

public class HtmlResponsesFeignConfig {

    @Bean
    public Decoder jsoupDecoder() {
        return (response, type) -> {
            try(InputStream is = response.body().asInputStream()) {
                return Jsoup.parse(is, Charset.defaultCharset().name(), response.request().url());
            }
        };
    }

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FormEncoder(new SpringEncoder(messageConverters));
    }


}
