package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workshop extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer duration;

    private Integer noOfAvailableSlots;

    @Enumerated(EnumType.STRING)
    private WorkshopType type;

    @Enumerated(EnumType.STRING)
    private WorkshopStatus status;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
