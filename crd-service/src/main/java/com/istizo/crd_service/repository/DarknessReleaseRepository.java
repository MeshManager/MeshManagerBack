package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.DarknessRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DarknessReleaseRepository extends JpaRepository<DarknessRelease, Long> {

    @Query("select dr.id from DarknessRelease dr where dr.serviceEntityId = :serviceEntityId")
    Long findDarknessReleaseIdByServiceEntityId(@Param("serviceEntityId") Long serviceEntityId);

    List<DarknessRelease> findAllByServiceEntityIdIn(List<Long> serviceEntityIds);
}
