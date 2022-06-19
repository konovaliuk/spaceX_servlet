package me.braun.spacex.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ServiceType {

    private byte id;
    private String service;
    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


}
