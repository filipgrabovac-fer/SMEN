package com.smen.Repositories;

import com.smen.Models.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWorkshopRepository extends JpaRepository<Workshop, Long> {
    List<Workshop> findByTitleContainingIgnoreCase(String title);

    List<Workshop> findByUserId(Long userId);

    List<Workshop> findByWorkshopStatusId(Long workshopStatusId);
}
