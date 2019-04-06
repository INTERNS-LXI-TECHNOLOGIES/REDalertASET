package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.EmergencyServiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EmergencyService and its DTO EmergencyServiceDTO.
 */
@Mapper(componentModel = "spring", uses = {AlertMapper.class})
public interface EmergencyServiceMapper extends EntityMapper<EmergencyServiceDTO, EmergencyService> {

    @Mapping(source = "alert.id", target = "alertId")
    EmergencyServiceDTO toDto(EmergencyService emergencyService);

    @Mapping(source = "alertId", target = "alert")
    EmergencyService toEntity(EmergencyServiceDTO emergencyServiceDTO);

    default EmergencyService fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmergencyService emergencyService = new EmergencyService();
        emergencyService.setId(id);
        return emergencyService;
    }
}
