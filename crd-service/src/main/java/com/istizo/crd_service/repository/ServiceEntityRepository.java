package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {

}
