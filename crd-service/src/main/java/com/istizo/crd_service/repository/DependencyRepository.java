package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependencyRepository extends JpaRepository<Dependency, Long> {

}
