package com.github.voragoth.drugstores.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.github.voragoth.drugstores.jackson.CustomBigDecimalDeserializer;
import com.github.voragoth.drugstores.jackson.CustomDayOfWeekDeserializer;
import com.github.voragoth.drugstores.jackson.CustomLocalTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugstoreFeignDTO {

    /**
     * La fecha de la consulta
     */
    @JsonProperty("fecha")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    /**
     * El id del local.
     */
    @JsonProperty("local_id")
    private String localId;

    /**
     * El nombre de la farmacia.
     */
    @JsonProperty("local_nombre")
    private String name;

    /**
     * El nombre de la comuna.
     */
    @JsonProperty("comuna_nombre")
    private String commune;

    /**
     * La localidad de la farmacia.
     */
    @JsonProperty("localidad_nombre")
    private String location;

    /**
     * La direccion de la farmacia.
     */
    @JsonProperty("local_direccion")
    private String address;

    /**
     * La hora de apertura de la farmacia.
     */
    @JsonDeserialize(using = CustomLocalTimeDeserializer.class)
    @JsonProperty("funcionamiento_hora_apertura")
    private LocalTime openingTime;

    /**
     * La hora de cierre de la farmacia.
     */
    @JsonDeserialize(using = CustomLocalTimeDeserializer.class)
    @JsonProperty("funcionamiento_hora_cierre")
    private LocalTime closingTime;

    /**
     * El telefono de la farmacia.
     */
    @JsonProperty("local_telefono")
    private String phone;

    /**
     * La latitud geografica de la farmacia.
     */
    @JsonProperty("local_lat")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal latitude;

    /**
     * La longitud geografica de la farmacia.
     */
    @JsonProperty("local_lng")
    @JsonDeserialize(using = CustomBigDecimalDeserializer.class)
    private BigDecimal longitude;

    /**
     * El dia de funcionamiento.
     */
    @JsonProperty("funcionamiento_dia")
    @JsonDeserialize(using = CustomDayOfWeekDeserializer.class)
    private DayOfWeek day;

    /**
     * El id de la region.
     */
    @JsonProperty("fk_region")
    private String regionId;

    /**
     * El id de la comuna.
     */
    @JsonProperty("fk_comuna")
    private String communeId;

    /**
     * El id de la comuna.
     */
    @JsonProperty("fk_localidad")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String locationId;
}
