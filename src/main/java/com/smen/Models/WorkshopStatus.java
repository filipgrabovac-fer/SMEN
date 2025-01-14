package com.smen.Models;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "workshop_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopStatus extends BaseEntity<BaseEntity, Number> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

}
