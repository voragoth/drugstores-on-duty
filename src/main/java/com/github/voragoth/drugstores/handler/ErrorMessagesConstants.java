package com.github.voragoth.drugstores.handler;

/**
 * Clase contenedora de constantes de mensajes de error.
 *
 * @author Manuel Vasquez Cruz.
 */
public final class ErrorMessagesConstants {

    /**
     * Constructor privado, creado para evitar la instanciacion de esta clase.
     *
     * @throws IllegalAccessException
     */
    private ErrorMessagesConstants() throws IllegalAccessException {
        throw new IllegalAccessException("Acceso denegado, clase contenedora de constantes");
    }

    /** Constante REGION COMMUNES NOT FOUND */
    public static final String COMMUNES_NOT_FOUND = "No se encontraron comunas";

    /** Constante REGIONS NOT FOUND */
    public static final String REGIONS_NOT_FOUND = "No se encontraron regiones";
}
