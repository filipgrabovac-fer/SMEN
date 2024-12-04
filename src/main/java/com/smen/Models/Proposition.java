package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class Proposition extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String tags;

    @Enumerated(EnumType.STRING)
    private PropositionStatus status;

    private Boolean approved;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
