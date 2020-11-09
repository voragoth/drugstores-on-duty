package com.github.voragoth.drugstores.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Clase de tests para Deserializadores personalizados de jackson en el proyecto.
 */
class DeserializersTest {


    /**
     * Test unitario para deserializar un string a bigdecimal
     *
     * @throws IOException si falla getText o deserialize.
     */
    @Test
    @DisplayName("Test unitario para deserializar un string numerico a bigdecimal")
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk1() throws IOException {
        // objetos necesarios
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("1.0").when(parser).getText();

        // test y asserts
        assertNotNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar un string vacio a bigdecimal null
     *
     * @throws IOException si falla getText o deserialize.
     */
    @Test
    @DisplayName("Test unitario para deserializar un string vacio a bigdecimal null")
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk2() throws IOException {
        // objetos necesarios
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn(" ").when(parser).getText();

        // test y asserts
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar un string no numerico a bigdecimal null
     *
     * @throws IOException si falla getText o deserialize.
     */
    @Test
    @DisplayName("Test unitario para deserializar un string no numerico a bigdecimal null")
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk3() throws IOException {
        // Objetos necesarios
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("T@").when(parser).getText();

        // test y assert
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar token no existente a bigdecimal null
     *
     * @throws IOException si falla deserialize.
     */
    @Test
    @DisplayName("Test unitario para deserializar token no existente a bigdecimal null")
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk4() throws IOException {
        // objeto necesario
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();

        // test y assert
        assertNull(cbdd.deserialize(mock(JsonParser.class), mock(DeserializationContext.class)));
    }

    /**
     * Test unitario para deserializar string a localtime
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar string correcto a localtime")
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk1() throws IOException {
        // Objetos necesarios
        CustomLocalTimeDeserializer cbdd = new CustomLocalTimeDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("15:00 hrs.").when(parser).getText();

        // test y assert
        assertNotNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar string vacio a localtime null
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar string vacio a localtime null")
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk2() throws IOException {

        // Objetos necesarios
        CustomLocalTimeDeserializer cbdd = new CustomLocalTimeDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn(" ").when(parser).getText();

        // test y assert
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar string incorrecto a localtime null
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar string incorrecto a localtime null")
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk3() throws IOException {
        // objetos necesarios
        CustomLocalTimeDeserializer cbdd = new CustomLocalTimeDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("T@").when(parser).getText();

        // test y assert.
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar sin token a localtime null
     *
     * @throws IOException si falla deserialize
     */
    @Test
    @DisplayName("Test unitario para deserializar sin token a localtime null")
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk4() throws IOException {
        // Objeto necesario
        CustomLocalTimeDeserializer cbdd = new CustomLocalTimeDeserializer();

        // test y assert
        assertNull(cbdd.deserialize(mock(JsonParser.class), mock(DeserializationContext.class)));
    }

    /**
     * Test unitario para deserializar string correcto a DayOfWeek
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar string correcto a DayOfWeek")
    void deserializeWithCustomDayOfWeekDeserializerShouldBeOk1() throws IOException {
        // Objetos necesarios.
        CustomDayOfWeekDeserializer cbdd = new CustomDayOfWeekDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("sabado").when(parser).getText();

        // test y asserts
        assertNotNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar string vacio a DayOfWeek null
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar string vacio a DayOfWeek null")
    void deserializeWithCustomDayOfWeekDeserializerShouldBeOk2() throws IOException {
        // Objetos necesarios
        CustomDayOfWeekDeserializer cbdd = new CustomDayOfWeekDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn(" ").when(parser).getText();

        // test y assert
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar string incorrecto a DayOfWeek null
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar string incorrecto a DayOfWeek null")
    void deserializeCustomDayOfWeekDeserializerShouldBeOk3() throws IOException {
        // Objetos necesarios
        CustomDayOfWeekDeserializer cbdd = new CustomDayOfWeekDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        // stubbing
        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("T@").when(parser).getText();

        // test y asserts
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    /**
     * Test unitario para deserializar TOKEN inexistente a DayOfWeek null
     *
     * @throws IOException si falla deserialize o getText
     */
    @Test
    @DisplayName("Test unitario para deserializar TOKEN inexistente a DayOfWeek null")
    void deserializeCustomDayOfWeekDeserializerShouldBeOk4() throws IOException {
        // Objetos necesarios
        CustomDayOfWeekDeserializer cbdd = new CustomDayOfWeekDeserializer();

        // test y assert
        assertNull(cbdd.deserialize(mock(JsonParser.class), mock(DeserializationContext.class)));
    }
}
