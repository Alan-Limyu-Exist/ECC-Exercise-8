package com.exist.ecc.limyu_exercise8.core.model.dto;

import com.exist.ecc.limyu_exercise8.core.model.Address;
import com.exist.ecc.limyu_exercise8.core.model.ContactInformation;
import com.exist.ecc.limyu_exercise8.core.model.Name;
import com.exist.ecc.limyu_exercise8.core.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private long id;

    @NotNull(message = "Name cannot be null")
    private Name name;

    @NotNull(message = "Address cannot be null")
    private Address address;

    private float gwa;

    private LocalDateTime dateHired;

    @NotNull(message = "Currently Employed cannot be null")
    private boolean currentlyEmployed;

    private ContactInformation contactInformation;

    private Set<Role> roles = new HashSet<>();

}
