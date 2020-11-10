package com.github.voragoth.drugstores.mapper;

import com.github.voragoth.drugstores.dto.Drugstore;
import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.feign.dto.DrugstoreFeignDTO;
import org.jsoup.nodes.Element;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    default Map<String, String> mapElementListToMap(List<Element> elements) {
        if (elements == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (Element el : elements) {
            map.put(el.text(), el.val());
        }
        return map;
    }

    /**
     * Metodo que mapea un objeto Drugstore desde un objeto DrugstoreVO.
     *
     * @param input el objeto DrugstoreVO a mapear
     * @return el objeto Drugstore resultante del mapeo.
     */
    Drugstore mapDrugstoreVOToDrugstore(DrugstoreVO input);

    /**
     * Metodo que mapea una lista de Drugstore desde una lista de DrugstoreVO.
     *
     * @param input la lista de DrugstoreVO a mapear
     * @return la lista de Drugstore resultante del mapeo.
     */
    List<Drugstore> mapDrugstoreVOListToDrugstoreList(List<DrugstoreVO> input);


    /**
     * Metodo que mapea un objeto DrugstoreFeignDTO desde un objeto DrugstoreVO.
     *
     * @param input el objeto DrugstoreFeignDTO a mapear
     * @return el objeto DrugstoreVO resultante del mapeo.
     */
    @Mapping(target = "onDuty", expression = "java(false)")
    DrugstoreVO mapDrugstoreFeignDTOToDrugstoreVO(DrugstoreFeignDTO input);

    /**
     * Metodo que mapea una lista de DrugstoreFeignDTO desde una lista de DrugstoreVO.
     *
     * @param input la lista de DrugstoreFeignDTO a mapear
     * @return la lista de DrugstoreVO resultante del mapeo.
     */
    List<DrugstoreVO> mapDrugstoreFeignDTOListToDrugstoreVOList(List<DrugstoreFeignDTO> input);
}
