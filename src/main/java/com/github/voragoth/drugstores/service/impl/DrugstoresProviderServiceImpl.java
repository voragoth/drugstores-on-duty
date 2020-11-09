package com.github.voragoth.drugstores.service.impl;

import com.github.voragoth.drugstores.dto.vo.DrugstoreVO;
import com.github.voragoth.drugstores.service.DrugstoresProviderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Implementacion del servicio que obtiene y filtra las farmacias de turno.
 *
 * @author Manuel Vasquez Cruz
 */
@Service
public class DrugstoresProviderServiceImpl implements DrugstoresProviderService {

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable("drugstores")
    public List<DrugstoreVO> getDrugStoresOnDuty(@NotNull String region) {
        throw new IllegalStateException("metodo aun no implementado");
    }
}
