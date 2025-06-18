package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DependencyRepository extends JpaRepository<Dependency, Long> {
    @Query("select d.id from Dependency d where d.serviceEntityId = :serviceEntityId")
    List<Long> findDependenciesIdByServiceEntityId(@Param("serviceEntityId") Long serviceEntityId);

    List<Dependency> findAllByServiceEntityIdIn(List<Long> serviceEntityIds);
}
