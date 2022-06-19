package me.braun.spacex.persistence.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
    private String passwordHash;

    public String getFullName(){
        return firstName + " " + lastName;
    }

}