package com.smen.Services;

import com.smen.Dto.ActivityLog.ActivityLogDto;
import com.smen.Models.ActivityLog;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Repositories.IActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityLogService extends BaseEntityService<ActivityLog, Long> {

    private final IActivityLogRepository activityLogRepository;

    public ActivityLogService(IActivityLogRepository activityLogRepository) {
        super(activityLogRepository);
        this.activityLogRepository = activityLogRepository;
    }

    // Fetch activity log by ID as a DTO
    public Optional<ActivityLogDto> getActivityLogDto(Long id) {
        return activityLogRepository.findById(id).map(ActivityLogDto::map);
    }

    // Fetch all activity logs as DTOs
    public List<ActivityLogDto> getAllActivityLogs() {
        return activityLogRepository.findAll()
                .stream()
                .map(ActivityLogDto::map)
                .collect(Collectors.toList());
    }

    // Fetch all activity logs by user ID as DTOs
    public List<ActivityLogDto> getActivityLogsByUser(Long userId) {
        return activityLogRepository.findByUserId(userId)
                .stream()
                .map(ActivityLogDto::map)
                .collect(Collectors.toList());
    }

    // Fetch all activity logs by workshop ID as DTOs
    public List<ActivityLogDto> getActivityLogsByWorkshop(Long workshopId) {
        return activityLogRepository.findByWorkshopId(workshopId)
                .stream()
                .map(ActivityLogDto::map)
                .collect(Collectors.toList());
    }

    // Save activity log
    public ActivityLogDto saveActivityLog(ActivityLogDto dto, User user, Workshop workshop) {
        ActivityLog activityLog = dto.toEntity(user, workshop);
        ActivityLog savedActivityLog = activityLogRepository.save(activityLog);
        return ActivityLogDto.map(savedActivityLog);
    }

    // Delete activity log
    public boolean deleteActivityLog(Long id) {
        if (activityLogRepository.existsById(id)) {
            activityLogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
