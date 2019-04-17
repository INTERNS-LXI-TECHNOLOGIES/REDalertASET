package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.AlertDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Alert and its DTO AlertDTO.
 */
@Mapper(componentModel = "spring", uses = {UserDomainMapper.class, LocationMapper.class})
public interface AlertMapper extends EntityMapper<AlertDTO, Alert> {

    @Mapping(source = "userDomain.id", target = "userDomainId")
    @Mapping(source = "location.id", target = "locationId")
    AlertDTO toDto(Alert alert);

    @Mapping(source = "userDomainId", target = "userDomain")
    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "emergencyServices", ignore = true)
    Alert toEntity(AlertDTO alertDTO);

    default Alert fromId(Long id) {
        if (id == null) {
            return null;
        }
        Alert alert = new Alert();
        alert.setId(id);
        return alert;
    }
}
