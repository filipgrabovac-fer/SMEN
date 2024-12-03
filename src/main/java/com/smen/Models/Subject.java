package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String tags;

    private String description;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Proposition> propositions;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Workshop> workshops;
}