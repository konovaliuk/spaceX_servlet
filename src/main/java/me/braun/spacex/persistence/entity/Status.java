package me.braun.spacex.persistence.entity;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Status {
    @Setter(AccessLevel.NONE)
    private Short id;
    private String status;


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
