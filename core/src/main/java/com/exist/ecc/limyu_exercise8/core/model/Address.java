package com.exist.ecc.limyu_exercise8.core.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

    private String streetNo;

    private String barangay;

    private String municipality;

    private int zipCode;
}
