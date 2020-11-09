package com.github.voragoth.drugstores.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class DeserializersTest {

    @Test
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk1() throws Exception {
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("1.0").when(parser).getText();
        assertNotNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk2() throws Exception {
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn(" ").when(parser).getText();

        assertNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk3() throws Exception {
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("T@").when(parser).getText();

        assertNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomBigDecimalDeserializerShouldBeOk4() throws Exception {
        CustomBigDecimalDeserializer cbdd = new CustomBigDecimalDeserializer();
        assertNull(cbdd.deserialize(mock(JsonParser.class), mock(DeserializationContext.class)));
    }


    @Test
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk1() throws Exception {
        CustomLocalTimeDeserializer  cbdd = new CustomLocalTimeDeserializer ();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("15:00 hrs.").when(parser).getText();
        assertNotNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk2() throws Exception {
        CustomLocalTimeDeserializer  cbdd = new CustomLocalTimeDeserializer ();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn(" ").when(parser).getText();
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk3() throws Exception {
        CustomLocalTimeDeserializer cbdd = new CustomLocalTimeDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("T@").when(parser).getText();

        assertNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomLocalTimeDeserializerShouldBeOk4() throws Exception {
        CustomLocalTimeDeserializer cbdd = new CustomLocalTimeDeserializer();
        assertNull(cbdd.deserialize(mock(JsonParser.class), mock(DeserializationContext.class)));
    }

    @Test
    void deserializeWithCustomDayOfWeekDeserializerShouldBeOk1() throws Exception {
        CustomDayOfWeekDeserializer  cbdd = new CustomDayOfWeekDeserializer ();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("sabado").when(parser).getText();
        assertNotNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeWithCustomDayOfWeekDeserializerShouldBeOk2() throws Exception {
        CustomDayOfWeekDeserializer  cbdd = new CustomDayOfWeekDeserializer ();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn(" ").when(parser).getText();
        assertNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeCustomDayOfWeekDeserializerShouldBeOk3() throws Exception {
        CustomDayOfWeekDeserializer cbdd = new CustomDayOfWeekDeserializer();
        JsonParser parser = mock(JsonParser.class);
        DeserializationContext ctxt = mock(DeserializationContext.class);

        doReturn(true).when(parser).hasToken(any(JsonToken.class));
        doReturn("T@").when(parser).getText();

        assertNull(cbdd.deserialize(parser, ctxt));
    }

    @Test
    void deserializeCustomDayOfWeekDeserializerShouldBeOk4() throws Exception {
        CustomDayOfWeekDeserializer cbdd = new CustomDayOfWeekDeserializer();
        assertNull(cbdd.deserialize(mock(JsonParser.class), mock(DeserializationContext.class)));
    }
}
