package com.github.voragoth.drugstores.config;

import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class HtmlResponsesFeignConfigTest {

    @Test
    void jsoupDecoderDecodeShouldBeOK() throws Exception {
        // objetos necesarios
        HtmlResponsesFeignConfig config = new HtmlResponsesFeignConfig();
        Decoder decoder = config.jsoupDecoder();
        Request request = Request.create(Request.HttpMethod.GET, "http://localhost/test",
                new HashMap<>(), new byte[0], Charset.defaultCharset(), new RequestTemplate());
        String body = "<option></option>";
        Response response = Response.builder()
                .request(request)
                .body(body.getBytes())
                .build();
        //test
        Object a = decoder.decode(response, mock(Type.class));

        // asserts
        assertNotNull(a);
    }
}
