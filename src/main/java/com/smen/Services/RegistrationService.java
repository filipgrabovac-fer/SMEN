package com.smen.Services;

import com.smen.Dto.Registration.RegistrationDto;
import com.smen.Models.Registration;
import com.smen.Repositories.IRegistrationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService extends BaseEntityService<Registration, Long> {

    private final IRegistrationRepository registrationRepository;

    public RegistrationService(IRegistrationRepository registrationRepository, IRegistrationRepository registrationRepository1) {
        super(registrationRepository);
        this.registrationRepository = registrationRepository1;
    }

    // Get all registrations for a specific user as DTOs
    public List<RegistrationDto> getRegistrationsByUser(Long userId) {
        return registrationRepository.findByUserId(userId)
                .stream()
                .map(RegistrationDto::map)
                .collect(Collectors.toList());
    }

    // Get all registrations for a specific workshop as DTOs
    public List<RegistrationDto> getRegistrationsByWorkshop(Long workshopId) {
        return registrationRepository.findByWorkshopId(workshopId)
                .stream()
                .map(RegistrationDto::map)
                .collect(Collectors.toList());
    }

    // Get all registrations as DTOs
    public List<RegistrationDto> getAllRegistrations() {
        return registrationRepository.findAll()
                .stream()
                .map(RegistrationDto::map)
                .collect(Collectors.toList());
    }

    // Create a new registration from DTO
    public RegistrationDto createRegistration(Registration registration) {
        Registration createdRegistration = registrationRepository.save(registration);
        return RegistrationDto.map(createdRegistration);
    }
}
