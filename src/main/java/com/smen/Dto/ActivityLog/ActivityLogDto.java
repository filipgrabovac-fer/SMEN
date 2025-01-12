package com.smen.Dto.ActivityLog;

import com.smen.Models.ActivityLog;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogDto {
    private Long id;
    private String activity;
    private String description;
    private Long userId;
    private Long workshopId;

    public static ActivityLogDto map(ActivityLog activityLog) {
        ActivityLogDto dto = new ActivityLogDto();
        dto.setId(activityLog.getId());
        dto.setActivity(activityLog.getActivity());
        dto.setDescription(activityLog.getDescription());
        dto.setUserId(activityLog.getUser() != null ? activityLog.getUser().getId() : null);
        dto.setWorkshopId(activityLog.getWorkshop() != null ? activityLog.getWorkshop().getId() : null);
        return dto;
    }

    public ActivityLog toEntity(User user, Workshop workshop) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setId(this.id);
        activityLog.setActivity(this.activity);
        activityLog.setDescription(this.description);
        activityLog.setUser(user);
        activityLog.setWorkshop(workshop);
        return activityLog;
    }
}