package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLog extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time;

    private String activity;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
