package com.smen.Services;

import com.smen.Models.ActivityLog;
import com.smen.Repositories.IActivityLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService extends BaseEntityService<ActivityLog, Long> {

    private final IActivityLogRepository activityLogRepository;

    public ActivityLogService(IActivityLogRepository activityLogRepository) {
        super(activityLogRepository);
        this.activityLogRepository = activityLogRepository;
    }

    //nije nuzno (kako nikako ne bi mogli stvoriti novi activitylog)
    @Override
    public ActivityLog create(ActivityLog activityLog) {
        throw new UnsupportedOperationException("Creation of ActivityLog is not allowed");
    }


    //sva aktivnost nekog korisnika
    public List<ActivityLog> getByUser(Long userId) {
        return activityLogRepository.findByUserId(userId);

    }
}
