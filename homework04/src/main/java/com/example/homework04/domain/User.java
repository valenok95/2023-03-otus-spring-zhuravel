package com.example.homework04.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private final String firstName;
    private final String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    ;
}
