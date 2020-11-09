package com.github.voragoth.drugstores.config;

import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Decoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Classe de test para HtmlResponsesFeignConfig
 */
class HtmlResponsesFeignConfigTest {

    /**
     * Test unitario para el decoder de HtmlResponsesFeignConfig
     *
     * @throws IOException
     */
    @Test
    @DisplayName("Test unitario para el decoder de HtmlResponsesFeignConfig")
    void jsoupDecoderDecodeShouldBeOK() throws IOException {
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
