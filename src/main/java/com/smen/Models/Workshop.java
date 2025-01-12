package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Workshop extends BaseEntity<BaseEntity, Number> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer duration;

    private Integer noOfAvailableSlots;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<WorkshopSubject> workshopSubjects;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<ActivityLog> activityLogs;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<Approval> approvals;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workshop_status_id")
    private WorkshopStatus workshopStatus;
}
