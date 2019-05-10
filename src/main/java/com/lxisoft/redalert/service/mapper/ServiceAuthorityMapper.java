package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.ServiceAuthorityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ServiceAuthority and its DTO ServiceAuthorityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceAuthorityMapper extends EntityMapper<ServiceAuthorityDTO, ServiceAuthority> {



    default ServiceAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceAuthority serviceAuthority = new ServiceAuthority();
        serviceAuthority.setId(id);
        return serviceAuthority;
    }
}
