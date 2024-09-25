package com.exist.ecc.limyu_exercise8.service;

import com.exist.ecc.limyu_exercise8.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role save(Role person);
}
