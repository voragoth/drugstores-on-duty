package com.github.voragoth.drugstores.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase para deserializar un string de hora con un sufijo a LocalTime.
 *
 * @author Manuel Vasquez Cruz.
 */
public class CustomLocalTimeDeserializer extends StdDeserializer<LocalTime> {

    /**
     * El formato.
     */
    public static final String FORMAT = "HH:mm";

    /**
     * El sufijo.
     */
    public static final String SUFFIX = " hrs.";


    /**
     * El formatter.
     */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMAT);

    public CustomLocalTimeDeserializer() {
        super(LocalTime.class);
    }

    /**
     * Metodo para deserializar un atributo json a LocalTime. Si no puede retorna un null.
     *
     * @param parser  el JsonParser
     * @param context el contexto
     * @return el LocalTime Parseado
     * @throws IOException si no puede parsearse
     */
    @Override
    public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            String string = parser.getText().trim();
            if (StringUtils.isBlank(string)) {
                return null;
            }
            string = StringUtils.remove(string, SUFFIX);
            try {
                return LocalTime.parse(string, FORMATTER);
            } catch (DateTimeException dte) {
                return null;
            }
        }
        return null;
    }
}
