package com.smen.Repositories;

import com.smen.Models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserId(Long userId);
    List<Registration> findByWorkshopId(Long workshopId);
}
