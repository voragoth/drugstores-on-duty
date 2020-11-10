package com.github.voragoth.drugstores.constants;

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

    /**
     * Constante REGION COMMUNES NOT FOUND
     */
    public static final String COMMUNES_NOT_FOUND = "No se encontraron comunas";

    /**
     * Constante REGIONS NOT FOUND
     */
    public static final String REGIONS_NOT_FOUND = "No se encontraron regiones";

    /**
     * Constante REGIONS NOT FOUND
     */
    public static final String DRUGSTORES_NOT_FOUND = "No se encontraron farmacias";

    /**
     * Constante DRUGSTORES BY REGION DOWN.
     */
    public static final String DRUGSTORES_BY_REGION_DOWN = "Proveedor de farmacias por region caido";

    /**
     * Constante DRUGSTORES ON DUTY DOWN.
     */
    public static final String DRUGSTORES_ON_DUTY_DOWN = "Proveedor de farmacias de turno caido";

    /**
     * Constante UNEXPECTED CONCURRENCES ERROR.
     */
    public static final String UNEXPECTED_CONCURRENCE_ERROR ="Error en concurrencia inesperado";

    /**
     * Constante CONCURRENCES ERROR.
     */
    public static final String CONCURRENCE_ERROR ="Error en concurrencia";

    /**
     * Constante SHORTCIRCUIT CAUSED BY.
     */
    public static final String SHORTCIRCUIT_CAUSED_BY = "Cortocircuito causado por ";

    /**
     * Constante COMMUNES DOWN.
     */
    public static final String COMMUNES_DOWN = "Proveedor de comunas caido";

    /**
     * Constante REGIONS DOWN.
     */
    public static final String REGIONS_DOWN = "Proveedor de regiones caido";

}
