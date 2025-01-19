package com.smen.Services;

import com.smen.DTO.ActivityLog.ActivityLogDto;
import com.smen.Models.ActivityLog;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Repositories.IActivityLogRepository;
import com.smen.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityLogService extends BaseEntityService<ActivityLog, Long> {

    private final IActivityLogRepository activityLogRepository;
    private final IUserRepository userRepository;

    public ActivityLogService(IActivityLogRepository activityLogRepository, UserService userService, IUserRepository userRepository) {
        super(activityLogRepository);
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    // Fetch activity log by ID as a DTO
    public Optional<ActivityLogDto> getActivityLogDto(Long id) {
        return activityLogRepository.findById(id).map(ActivityLogDto::map);
    }

    // Fetch all activity logs as DTOs
    public List<ActivityLogDto> getAllActivityLogs() {

        return activityLogRepository.findAll()
                .stream()
                .map(activityLog  ->{
                    ActivityLogDto activityLogDto = new ActivityLogDto();
                    User user = userRepository.findById(activityLog.getUserId()).get();

                    activityLogDto.setActivity(activityLog.getActivity());
                    activityLogDto.setId(activityLog.getId());
                    activityLogDto.setDescription(activityLog.getDescription());
                    activityLogDto.setUserId(activityLog.getUserId() != null ? activityLog.getUserId() : null);
                    activityLogDto.setUser(user.getUsername());
                    activityLogDto.setCreatedAt(activityLog.getCreatedAt().toString());
                    return activityLogDto;
                })
                .collect(Collectors.toList());
    }

    // Fetch all activity logs by user ID as DTOs
    public List<ActivityLogDto> getActivityLogsByUser(Long userId) {
        return activityLogRepository.findByUserId(userId)
                .stream()
                .map(ActivityLogDto::map)
                .collect(Collectors.toList());
    }



    // Save activity log
    public ActivityLogDto saveActivityLog(ActivityLogDto dto) {
        ActivityLog activityLog = dto.toEntity();
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
