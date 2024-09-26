package com.exist.ecc.limyu_exercise8.core.controller;

import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Role create(@Valid @RequestBody Role role) {
        return roleService.save(role);
    }
}
