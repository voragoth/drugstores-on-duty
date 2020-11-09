package com.github.voragoth.drugstores.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Clase que estructura los datos perteneciente a una farmacia
 *
 * @author Manuel Vasquez Cruz.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drugstore {

    /**
     * El nombre de la farmacia.
     */
    @JsonProperty("local")
    private String name;

    /**
     * La direccion de la farmacia.
     */
    @JsonProperty("direccion")
    private String address;

    /**
     * El telefono de la farmacia.
     */
    @JsonProperty("telefono")
    private String phone;

    /**
     * La latitud geografica de la farmacia.
     */
    @JsonProperty("latitude")
    private BigDecimal latitude;

    /**
     * La longitud geografica de la farmacia.
     */
    @JsonProperty("longitud")
    private BigDecimal longitude;
}
