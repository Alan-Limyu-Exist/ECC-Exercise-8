package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.exception.RoleAlreadyExistsException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.model.dto.RoleDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDto save(RoleDto roleDto) {
        if (roleRepository.existsByName(roleDto.getName())) {
            throw new RoleAlreadyExistsException("Role '" + roleDto.getName() + "' already exists in database");
        }
        return this.toDto(roleRepository.save(this.fromDto(roleDto)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public RoleDto get(UUID uuid) {
        RoleDto roleDto = this.toDto(roleRepository.findByUuid(uuid).orElse(null));

        if (roleDto == null) {
            throw new RoleNotFoundException("Cannot find role");
        }

        return roleDto;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public RoleDto update(UUID uuid, RoleDto role) {
        RoleDto updatedRole = get(uuid);
        updatedRole.setName(role.getName());
        return this.save(updatedRole);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public void delete(RoleDto roleDto) {
        UUID roleUuid = roleDto.getUuid();
        String roleName = roleDto.getName();
        roleDto = this.toDto(roleRepository.getByName(roleName));

        if (roleDto == null) {
            roleDto = get(roleUuid);
        }

        roleRepository.delete(fromDto(roleDto));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'VIEWER')")
    public void deleteByUuid(UUID uuid) {
        roleRepository.delete(this.fromDto(get(uuid)));
    }

    @Override
    public RoleDto toDto(Role role) {
        if (role == null) {
            return null;
        }

        return new RoleDto(
                role.getId(),
                role.getUuid(),
                role.getName()
        );
    }

    @Override
    public Role fromDto(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }

        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }
}
