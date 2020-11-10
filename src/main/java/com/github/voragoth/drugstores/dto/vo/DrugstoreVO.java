package com.github.voragoth.drugstores.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Clase interna que estructura los datos perteneciente a una farmacia
 *
 * @author Manuel Vasquez Cruz.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugstoreVO {

    /**
     * El nombre de la farmacia.
     */
    private String name;

    /**
     * La direccion de la farmacia.
     */
    private String address;

    /**
     * El telefono de la farmacia.
     */
    private String phone;

    /**
     * La latitud geografica de la farmacia.
     */
    private BigDecimal latitude;

    /**
     * La longitud geografica de la farmacia.
     */
    private BigDecimal longitude;

    /**
     * El id de la comuna.
     */
    private String communeId;

    /**
     * El id de la region.
     */
    private String regionId;

    /**
     * El id de la region.
     */
    private String localId;

    /**
     * Bandera si la farmacia esta de turno.
     */
    private boolean onDuty;

}