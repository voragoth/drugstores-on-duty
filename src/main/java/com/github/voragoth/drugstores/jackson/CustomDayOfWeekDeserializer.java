package com.github.voragoth.drugstores.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para deserializar un string representan un dia a DayOfWeek.
 *
 * @author Manuel Vasquez Cruz.
 */
public class CustomDayOfWeekDeserializer extends StdDeserializer<DayOfWeek> {

    /**
     * El mapeo de dias a DayOfWeek
     */
    private static final Map<String, DayOfWeek> DAYS = new HashMap<>();

    /**
     * El constructor
     */
    public CustomDayOfWeekDeserializer() {
        super(DayOfWeek.class);
        if (MapUtils.isEmpty(DAYS)) {
            DAYS.put("lunes", DayOfWeek.MONDAY);
            DAYS.put("martes", DayOfWeek.TUESDAY);
            DAYS.put("miercoles", DayOfWeek.WEDNESDAY);
            DAYS.put("jueves", DayOfWeek.THURSDAY);
            DAYS.put("viernes", DayOfWeek.FRIDAY);
            DAYS.put("sabado", DayOfWeek.SATURDAY);
            DAYS.put("domingo", DayOfWeek.SUNDAY);
        }
    }

    /**
     * Metodo para deserializar un atributo json a DayOfWeek. Si no puede retorna un null.
     *
     * @param parser  el JsonParser
     * @param context el contexto
     * @return el DayOfWeek Parseado
     * @throws IOException si no puede parsearse
     */
    @Override
    public DayOfWeek deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            String string = parser.getText().trim();
            if (StringUtils.isBlank(string)) {
                return null;
            }
            if (DAYS.containsKey(string)) {
                return DAYS.get(string);
            }
        }
        return null;

    }
}
