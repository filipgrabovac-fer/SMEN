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

    private Long ownerId;

    private Long workshopStatusId;
}
