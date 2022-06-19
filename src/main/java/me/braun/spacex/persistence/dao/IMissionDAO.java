package me.braun.spacex.persistence.dao;

import me.braun.spacex.persistence.entity.Mission;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface IMissionDAO extends  DAO<Mission, Long>{
     int customerMissionsAmount(Long custId);
     @NotNull List<Mission> findAllPagination(int offset, int limit);
     @NotNull List<Mission> findByCustomer(Long custId, int limit, int offset );
     Optional<Mission> findByTitle(String title);
}
