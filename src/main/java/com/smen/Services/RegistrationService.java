package com.smen.Services;

import com.smen.Models.Registration;
import com.smen.Repositories.IRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService extends BaseEntityService<Registration, Long> {

    private final IRegistrationRepository registrationRepository;

    public RegistrationService(IRegistrationRepository registrationRepository, IRegistrationRepository registrationRepository1) {
        super(registrationRepository);
        this.registrationRepository = registrationRepository1;
    }

    //sve registracije nekog korisnika
    public List<Registration> getRegistrationsByUser(Long userId) {
        return registrationRepository.findByUserId(userId);

    }

    //sve registracije neke radionice
    public List<Registration> getRegistrationsByWorkshop(Long workshopId) {
        return registrationRepository.findByWorkshopId(workshopId);
    }
}
