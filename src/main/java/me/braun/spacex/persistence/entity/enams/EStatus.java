package me.braun.spacex.persistence.entity.enams;

public enum EStatus {
    PROCESS((short) 1),
    ACCEPT((short) 2),
    REjECT((short) 3),
    PAID((short) 4),
    CANCEL((short) 5),
    PREPER((short) 6),
    READY((short) 7),
    LAUNCH((short) 8),
    EXECUTE((short) 9),
    COMPLETE((short) 10),
    FAILED((short) 11);

    short id;
    EStatus(short id){ this.id = id;}
    public short getId(){return id;}
}
