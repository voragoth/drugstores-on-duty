package com.github.voragoth.drugstores.constants;

/**
 * Clase contenedora de constantes de la aplicacion.
 *
 * @author Manuel Vasquez Cruz.
 */
public final class ApplicationConstants {

    /**
     * Constructor privado, creado para evitar la instanciacion de esta clase.
     *
     * @throws IllegalAccessException
     */
    private ApplicationConstants() throws IllegalAccessException {
        throw new IllegalAccessException("Acceso denegado, clase contenedora de constantes");
    }

    /**
     * La constante REGION ID FEIGN KEY para el parametro de feign
     */
    public static final String REGION_ID_FEIGN_KEY = "reg_id";
}
