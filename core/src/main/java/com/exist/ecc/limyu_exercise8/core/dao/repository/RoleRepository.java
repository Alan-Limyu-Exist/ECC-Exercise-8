package com.exist.ecc.limyu_exercise8.core.dao.repository;

import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    default boolean existsByName(String name) {
        return this.findAll().stream()
                .anyMatch(role -> role.getName().equals(name));
    }

    default Role getByName(String name) {
        return this.findAll().stream()
                .filter(role -> role.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
