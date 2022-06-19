package me.braun.spacex.persistence.dao;

import me.braun.spacex.persistence.entity.Spacecraft;

import java.util.List;
import java.util.Optional;

public interface ISpacecraftDAO extends DAO<Spacecraft, Long>{
    List<String> getTitles() ;
    Optional<Spacecraft> findByTitle(String title);

}
