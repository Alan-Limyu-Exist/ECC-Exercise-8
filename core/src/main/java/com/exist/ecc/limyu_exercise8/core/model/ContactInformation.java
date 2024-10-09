package com.exist.ecc.limyu_exercise8.core.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ContactInformation {

    @Pattern(regexp = "^[0-9]{7,9}$", message = "Invalid landline number")
    private String landline;

    @Pattern(regexp = "^\\+?[0-9]{11,12}$", message = "Invalid mobile number")
    private String mobileNumber;

    @Email(message = "Email should be valid")
    private String email;
}
