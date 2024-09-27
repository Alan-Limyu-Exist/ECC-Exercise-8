package com.exist.ecc.limyu_exercise8.core.controller;

import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAllRoles();
    }

    @PostMapping
    public Role create(@Valid @RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable long id, @Valid @RequestBody Role role) {
        return roleService.update(id, role);
    }

    @DeleteMapping
    public void delete( @Valid @RequestBody Role role) {
        roleService.delete(role);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        roleService.deleteById(id);
    }
}
