package com.exist.ecc.limyu_exercise8.core.service;

import com.exist.ecc.limyu_exercise8.core.dao.repository.RoleRepository;
import com.exist.ecc.limyu_exercise8.core.exception.RoleAlreadyExistsException;
import com.exist.ecc.limyu_exercise8.core.exception.RoleNotFoundException;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import com.exist.ecc.limyu_exercise8.core.model.dto.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleServiceImplTest {

    @Spy
    @InjectMocks
    private RoleService roleServiceImpl;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private Role role;

    @Spy
    private RoleDto roleDto;

    @BeforeEach
    public void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleServiceImpl = spy(new RoleServiceImpl(roleRepository));

        role = new Role();
        role.setId(1L);
        role.setName("Admin");

        roleDto = this.roleServiceImpl.toDto(role);

        doAnswer(invocation -> role)
                .when(roleRepository)
                .save(any(Role.class));

        when(roleRepository.findByUuid(role.getUuid()))
                .thenReturn(Optional.ofNullable(role));

        doReturn(role).when(roleServiceImpl).fromDto(roleDto);
        doReturn(roleDto).when(roleServiceImpl).toDto(role);
    }

    @Test
    public void shouldGetAllPeople() {
        roleServiceImpl.getAllRoles();
        verify(roleRepository).findAll();
    }

    @Test
    public void shouldAddRole() {
        roleServiceImpl.save(roleDto);
    }

    @Test
    public void shouldNotAddRole() {
        when(roleRepository.existsByName("Admin")).thenReturn(true);
        assertThrows(RoleAlreadyExistsException.class,
                () -> roleServiceImpl.save(roleDto));
    }

    @Test
    public void shouldUpdateRole() {
        RoleDto newRole = new RoleDto();
        newRole.setId(2);
        newRole.setName("Admin 2");

        RoleDto updatedRole = roleServiceImpl.update(role.getUuid(), newRole);

        assertEquals(newRole.getName(), updatedRole.getName());
        assertNotEquals(newRole.getId(), updatedRole.getId());
    }

    @Test
    public void shouldNotUpdateRole() {
        RoleDto newRole = new RoleDto();
        newRole.setId(2);
        newRole.setName("Admin");

        when(roleRepository.existsByName("Admin")).thenReturn(true);
        assertThrows(RoleAlreadyExistsException.class,
                () -> roleServiceImpl.update(role.getUuid(), newRole));
    }

    @Test
    public void shouldDeleteRole() {
        when(roleRepository.getByName(role.getName())).thenReturn(role);

        roleServiceImpl.delete(roleDto);

        verify(roleRepository).delete(role);
    }

    @Test
    public void shouldNotDeleteRole() {
        RoleDto newRole = new RoleDto();
        newRole.setId(2);
        newRole.setName("Admin 2");

        assertThrows(RoleNotFoundException.class,
                () -> roleServiceImpl.delete(newRole));
    }

    @Test
    public void shouldDeleteRoleByUuid() {
        roleServiceImpl.deleteByUuid(role.getUuid());

        verify(roleRepository).delete(role);
    }

    @Test
    public void shouldNotDeleteRoleByUuid() {
        assertThrows(RoleNotFoundException.class,
                () -> roleServiceImpl.deleteByUuid(UUID.randomUUID()));
    }
}
