package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity(name = "smen_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String team;

    private String role;

    private Boolean anonymity;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ActivityLog> activityLogs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Workshop> workshop;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
}
