package com.smen.DTO.Registration;

import com.smen.Models.Registration;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private Long id;
    private Long userId;
    private Long workshopId;

    public static RegistrationDto map(Registration registration) {
        RegistrationDto dto = new RegistrationDto();
        dto.setId(registration.getId());
        dto.setUserId(registration.getUserId() != null ? registration.getUserId() : null);
        dto.setWorkshopId(registration.getWorkshopId() != null ? registration.getWorkshopId() : null);
        return dto;
    }

    public Registration toEntity(Long userId, Long workshopId) {
        Registration registration = new Registration();
        registration.setId(this.id);
        registration.setUserId(userId);
        registration.setWorkshopId(workshopId);
        return registration;
    }
}