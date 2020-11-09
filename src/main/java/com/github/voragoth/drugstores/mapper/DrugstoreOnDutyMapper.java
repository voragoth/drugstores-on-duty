package com.github.voragoth.drugstores.mapper;

import org.jsoup.nodes.Element;
import org.mapstruct.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * El mapper de la aplicacion, utilizando mapstruct.
 *
 * @author Manuel Vasquez Cruz.
 */
@Mapper(componentModel = "spring")
public interface DrugstoreOnDutyMapper {

    /**
     * Metodo que mapea entre una lista de elementos de JSoup a un hashmap con el texto y el val de las etiquetas.
     *
     * @param elements los elementos a mapear
     * @return el hashmap con el nuevo map.
     */
    default Map<String, Integer> mapElementListToMap(List<Element> elements) {
        if (elements == null) {
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        for (Element el : elements) {
            map.put(el.text(), Integer.valueOf(el.val()));
        }
        return map;
    }

}
