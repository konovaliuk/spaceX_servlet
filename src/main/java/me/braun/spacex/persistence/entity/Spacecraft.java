package me.braun.spacex.persistence.entity;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Spacecraft {

    private Long id;
    private String craftTitle;
    private Double capacity;
    private Integer maxWeight;
    private Integer launchPrice;


}