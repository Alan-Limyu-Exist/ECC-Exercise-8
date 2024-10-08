package com.exist.ecc.limyu_exercise8.core.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ContactInformation {

    private String landline;

    private String mobileNumber;

    private String email;
}
