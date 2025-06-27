package com.istizo.crd_service.service.RatioService;

import com.istizo.crd_service.domain.RatioSchedule;
import com.istizo.crd_service.domain.ServiceEntity;
import com.istizo.crd_service.repository.RatioScheduleRepository;
import com.istizo.crd_service.repository.ServiceEntityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatioScheduleService {
    private final RatioScheduleRepository ratioScheduleRepository;
    private final ServiceEntityRepository serviceEntityRepository;

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void applyScheduledRatios() {
        long now = System.currentTimeMillis();
        List<RatioSchedule> schedules = ratioScheduleRepository
                .findByTriggerTimeLessThanEqual(now);

        // ID 목록 추출
        List<Long> scheduleIds = new ArrayList<>();
        Map<Long, Integer> updates = new HashMap<>();

        for (RatioSchedule schedule : schedules) {
            scheduleIds.add(schedule.getId());
            updates.put(schedule.getServiceEntity().getId(), schedule.getNewRatio());
        }

        // 벌크 업데이트
        updateRatios(updates);

        // 벌크 삭제
        ratioScheduleRepository.deleteByIdIn(scheduleIds);

    }

    @Transactional
    public void updateRatios(Map<Long, Integer> idToRatio) {
        idToRatio.forEach(serviceEntityRepository::updateRatio);
    }

}

