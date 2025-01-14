package com.smen.Repositories;

import com.smen.Models.Workshop;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWorkshopRepository extends JpaRepository<Workshop, Long> {
    List<Workshop> findByTitleContainingIgnoreCase(String title);

    List<Workshop> findByOwnerId(Long userId);

    List<Workshop> findByWorkshopStatusId(Long workshopStatusId);

    List<Workshop> findByNoOfAvailableSlotsGreaterThan(Integer noOfAvailableSlots);
}
