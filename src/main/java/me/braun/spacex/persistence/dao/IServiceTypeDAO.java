package me.braun.spacex.persistence.dao;

import me.braun.spacex.persistence.entity.ServiceType;

import java.util.List;

public interface IServiceTypeDAO extends DAO<ServiceType, Byte>{
    List<String> findAllServices();
}
