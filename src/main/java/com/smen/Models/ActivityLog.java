package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog extends BaseEntity<BaseEntity, Number> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activity;

    private String description;

    private Long userId;
    
    public ActivityLog(String activity, String description, Long userId) {
        if(activity.equals("c")) this.activity = "Create";
        if(activity.equals("e")) this.activity = "Edit";
        if(activity.equals("d")) this.activity = "Delete";
        this.activity = activity;
        this.description = description;
        this.userId = userId;
    }
}
