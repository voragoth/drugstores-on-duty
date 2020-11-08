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

    /** Constante REGION MUST BE POSITIVE */
    public static final String REGION_MUST_BE_POSITIVE = "El valor de region debe ser positivo";
}
