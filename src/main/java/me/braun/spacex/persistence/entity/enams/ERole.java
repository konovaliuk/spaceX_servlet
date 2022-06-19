package me.braun.spacex.persistence.entity.enams;

public enum ERole {
    CUSTOMER((byte) 1),
    ADMIN((byte) 2),
    CURATOR((byte) 3);
    byte id;
    ERole(byte id){
        this.id = id;
    }

    public byte getId() {
        return id;
    }
}
