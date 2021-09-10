package com.ducklings.domain.distribution.repositories;

import com.ducklings.domain.distribution.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Integer> {
    @Query(value = "SELECT * " +
            "FROM distribution " +
            "WHERE regionid = :regionId AND date_part('year', datestart) = :year", nativeQuery = true)
    List<Distribution> getSearchResult(@Param("year") int year, @Param("regionId") int regionId);
}
