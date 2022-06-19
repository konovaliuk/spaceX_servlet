package me.braun.spacex.persistence.dto;

public interface DTO {
    default String toJson(){
        return null;
    }

}
