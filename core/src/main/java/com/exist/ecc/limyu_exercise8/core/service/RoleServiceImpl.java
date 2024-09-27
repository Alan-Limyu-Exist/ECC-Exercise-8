package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.exception.RoleAlreadyExistsException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final EntityManager entityManager;


    public RoleServiceImpl(RoleRepository roleRepository, EntityManager entityManager) {
        this.roleRepository = roleRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new RoleAlreadyExistsException("Role '" + role.getName() + "' already exists in database");
        }
        return roleRepository.save(role);
    }

    private Role get(long id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new RoleNotFoundException("Cannot find role");
        }

        return role;
    }

    @Override
    public Role update(long id, Role role) {
        Role updatedRole = get(id);
        entityManager.detach(updatedRole);
        updatedRole.setName(role.getName());
        return this.save(updatedRole);
    }

    @Override
    public void delete(Role role) {
        long roleId = role.getId();
        String roleName = role.getName();
        role = roleRepository.getByName(roleName);

        if (role == null) {
            role = get(roleId);
        }

        roleRepository.delete(role);
    }

    @Override
    public void deleteById(long id) {
        get(id);
        roleRepository.deleteById(id);
    }
}
