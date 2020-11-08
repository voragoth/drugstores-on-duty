package com.github.voragoth.drugstores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Clase principal para arrancar el server y la aplicacion spring boot.
 *
 * @author Manuel Vasquez Cruz.
 */
@SpringBootApplication
public class DrugstoresOnDutyApplication {

    /**
     * Metodo principal donde inicia la aplicacion de spring boot.
     *
     * @param args argumentos de la linea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(DrugstoresOnDutyApplication.class, args);
    }

}
