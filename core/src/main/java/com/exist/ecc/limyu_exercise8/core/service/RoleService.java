package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role save(Role person);
}
