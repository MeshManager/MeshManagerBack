package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    @Query("select s.id from ServiceEntity s where s.uuid = :uuid")
    List<Long> findIdByUuid(@Param("uuid") UUID uuid);

    List<ServiceEntity> findAllByUuid(UUID uuid);

    @Modifying
    @Query("UPDATE ServiceEntity se SET se.ratio = :newRatio WHERE se.id = :id")
    void updateRatio(@Param("id") Long id, @Param("newRatio") Integer newRatio);
}
