package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.DarknessRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DarknessReleaseRepository extends JpaRepository<DarknessRelease, Long> {

}
