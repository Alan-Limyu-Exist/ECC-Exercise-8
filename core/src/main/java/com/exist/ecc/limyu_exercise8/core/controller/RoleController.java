package com.exist.ecc.limyu_exercise8.core.controller;

import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.model.dto.RoleDto;
import com.exist.ecc.limyu_exercise8.core.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@Valid @RequestBody RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.save(roleDto));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RoleDto> update(@PathVariable UUID uuid, @Valid @RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(roleService.update(uuid, roleDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@Valid @RequestBody RoleDto roleDto) {
        roleService.delete(roleDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID uuid) {
        roleService.deleteByUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}
