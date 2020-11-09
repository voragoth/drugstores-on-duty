package com.github.voragoth.drugstores.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class CustomBigDecimalDeserializer extends StdDeserializer<BigDecimal> {

    public static final String FORMAT = "###.###############";

    private static final DecimalFormat FORMATTER = new DecimalFormat(FORMAT);


    protected CustomBigDecimalDeserializer() {
        super(BigDecimal.class);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.FRANCE);
        symbols.setDecimalSeparator('.');
        FORMATTER.setDecimalFormatSymbols(symbols);
        FORMATTER.setParseBigDecimal(true);
    }

    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            String string = parser.getText().trim();
            if (StringUtils.isBlank(string)) {
                return null;
            }
            try {
                return (BigDecimal) FORMATTER.parse(string);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }
}
