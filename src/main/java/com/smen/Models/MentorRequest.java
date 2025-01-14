package com.smen.Models;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class MentorRequest extends BaseEntity<BaseEntity, Number> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String comment;

    @NonNull
    private Long requesterId;

    @Nullable
    private Long reviewerId;

    @NonNull
    private Long mentorRequestStatusId;
}
