package com.smen.Dto.Registration;

import com.smen.Models.Registration;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private Long id;
    private Long userId;
    private Long workshopId;

    public static RegistrationDto map(Registration registration) {
        RegistrationDto dto = new RegistrationDto();
        dto.setId(registration.getId());
        dto.setUserId(registration.getUser() != null ? registration.getUser().getId() : null);
        dto.setWorkshopId(registration.getWorkshop() != null ? registration.getWorkshop().getId() : null);
        return dto;
    }

    public Registration toEntity(User user, Workshop workshop) {
        Registration registration = new Registration();
        registration.setId(this.id);
        registration.setUser(user);
        registration.setWorkshop(workshop);
        return registration;
    }
}