package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    List<Long> findIdByUuid(UUID uuid);
}
