package me.braun.spacex.persistence.entity.enams;

public enum EServices {
    SATTERLINE((byte) 1),
    SPACE_STATION((byte) 2),
    TELESCOPE((byte) 3),
    DELIVER_CARGO((byte) 4),
    ORBITAL((byte) 5),
    MOON((byte) 6),
    MERCURIY((byte) 7),
    VENUS((byte) 8),
    MARS((byte) 9),
    JUPITER((byte) 10),
    SATURN((byte) 11),
    OTHER_PLANET((byte) 12),
    BEYONG_SOLAR_SYSTEM((byte) 13);


    byte id;
    EServices(byte id){this.id = id;}
    public byte getId(){return id;}
}
