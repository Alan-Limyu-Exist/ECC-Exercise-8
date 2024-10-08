package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.model.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();

    RoleDto save(RoleDto roleDto);

    RoleDto update(long id, RoleDto roleDto);

    void delete(RoleDto roleDto);

    void deleteById(long id);

    RoleDto toDto(Role role);

    Role fromDto(RoleDto roleDto);
}
