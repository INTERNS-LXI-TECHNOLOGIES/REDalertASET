package com.lxisoft.redalert.service.mapper;

import com.lxisoft.redalert.domain.*;
import com.lxisoft.redalert.service.dto.UserDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserDomain and its DTO UserDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class, RoleMapper.class})
public interface UserDomainMapper extends EntityMapper<UserDomainDTO, UserDomain> {


    @Mapping(target = "alerts", ignore = true)
    UserDomain toEntity(UserDomainDTO userDomainDTO);

    default UserDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserDomain userDomain = new UserDomain();
        userDomain.setId(id);
        return userDomain;
    }
}
