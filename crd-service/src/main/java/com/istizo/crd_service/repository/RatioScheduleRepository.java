package com.istizo.crd_service.repository;

import com.istizo.crd_service.domain.RatioSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatioScheduleRepository extends JpaRepository<RatioSchedule, Long> {
    // ServiceEntity ID로 RatioSchedule 삭제
    @Modifying
    @Query("DELETE FROM RatioSchedule rs WHERE rs.serviceEntity.id = :serviceEntityId")
    void deleteByServiceEntityId(@Param("serviceEntityId") Long serviceEntityId);

    // ServiceEntity ID로 RatioSchedule 조회
    List<RatioSchedule> findByServiceEntityId(Long serviceEntityId);

    @Query("SELECT rs FROM RatioSchedule rs WHERE rs.triggerTime <= :currentTime")
    List<RatioSchedule> findByTriggerTimeLessThanEqual(@Param("currentTime") Long currentTime);

    // RatioScheduleRepository.java
    @Modifying
    @Query("DELETE FROM RatioSchedule rs WHERE rs.id IN :ids")
    void deleteByIdIn(@Param("ids") List<Long> ids);

}
