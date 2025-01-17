package com.smen.Repositories;

import com.smen.Models.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByUserId(Long userId);

}
