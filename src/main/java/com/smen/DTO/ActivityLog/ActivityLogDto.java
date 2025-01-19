package com.smen.DTO.ActivityLog;

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
    private String user;
    private String createdAt;

    public ActivityLogDto(String activity, String description, Long userId) {
        if(activity.equals("c")) this.activity = "Create";
        if(activity.equals("e")) this.activity = "Edit";
        if(activity.equals("d")) this.activity = "Delete";
        this.description = description;
        this.userId = userId;
    }

    public static ActivityLogDto map(ActivityLog activityLog) {
        ActivityLogDto dto = new ActivityLogDto();
        dto.setActivity(activityLog.getActivity());
        dto.setDescription(activityLog.getDescription());
        dto.setUserId(activityLog.getUserId() != null ? activityLog.getUserId() : null);
        return dto;
    }

    public ActivityLog toEntity() {
        return  new ActivityLog(this.activity, this.description, this.userId);
    }
}