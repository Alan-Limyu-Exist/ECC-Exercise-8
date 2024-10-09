package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.model.dto.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleDto> getAllRoles();

    RoleDto save(RoleDto roleDto);

    RoleDto update(UUID uuid, RoleDto roleDto);

    void delete(RoleDto roleDto);

    void deleteByUuid(UUID uuid);

    RoleDto toDto(Role role);

    Role fromDto(RoleDto roleDto);
}
