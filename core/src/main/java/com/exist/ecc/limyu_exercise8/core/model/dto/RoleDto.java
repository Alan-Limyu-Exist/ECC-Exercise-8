package com.exist.ecc.limyu_exercise8.core.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private long id;

    private UUID uuid;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
}
