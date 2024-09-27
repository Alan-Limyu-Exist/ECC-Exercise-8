package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.exception.RoleAlreadyExistsException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceImplTest {

    @InjectMocks
    private RoleService roleServiceImpl;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private Role role;

    @BeforeEach
    public void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleServiceImpl = new RoleServiceImpl(roleRepository);

        role = new Role();
        role.setId(1L);
        role.setName("Admin");

        doAnswer(invocation -> role)
                .when(roleRepository)
                .save(any(Role.class));

        when(roleRepository.findById(1L))
                .thenReturn(Optional.ofNullable(role));
    }

    @Test
    public void shouldGetAllPeople() {
        roleServiceImpl.getAllRoles();
        verify(roleRepository).findAll();
    }

    @Test
    public void shouldAddRole() {
        roleServiceImpl.save(role);
    }

    @Test
    public void shouldNotAddRole() {
        when(roleRepository.existsByName("Admin")).thenReturn(true);
        assertThrows(RoleAlreadyExistsException.class,
                () -> roleServiceImpl.save(role));
    }

    @Test
    public void shouldUpdateRole() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("Admin 2");

        Role updatedRole = roleServiceImpl.update(1L, newRole);

        assertEquals(newRole.getName(), updatedRole.getName());
        assertNotEquals(newRole.getId(), updatedRole.getId());
    }

    @Test
    public void shouldNotUpdateRole() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("Admin");

        when(roleRepository.existsByName("Admin")).thenReturn(true);
        assertThrows(RoleAlreadyExistsException.class,
                () -> roleServiceImpl.update(1L, newRole));
    }

    @Test
    public void shouldDeleteRole() {
        roleServiceImpl.delete(role);

        verify(roleRepository).delete(role);
    }

    @Test
    public void shouldNotDeleteRole() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("Admin 2");

        assertThrows(RoleNotFoundException.class,
                () -> roleServiceImpl.delete(newRole));
    }

    @Test
    public void shouldDeleteRoleById() {
        roleServiceImpl.deleteById(1L);

        verify(roleRepository).deleteById(1L);
    }

    @Test
    public void shouldNotDeleteRoleById() {
        assertThrows(RoleNotFoundException.class,
                () -> roleServiceImpl.deleteById(2L));
    }
}
