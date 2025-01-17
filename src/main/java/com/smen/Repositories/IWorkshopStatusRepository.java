package com.smen.Repositories;

import com.smen.Models.WorkshopStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IWorkshopStatusRepository extends JpaRepository<WorkshopStatus, Long> {
    Optional<WorkshopStatus> findByName(String name);
    Optional<WorkshopStatus> getWorkshopStatusById(Long id);
}
