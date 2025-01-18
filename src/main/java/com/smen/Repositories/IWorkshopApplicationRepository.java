package com.smen.Repositories;

import com.smen.Models.Post;
import com.smen.Models.WorkshopApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWorkshopApplicationRepository  extends JpaRepository<WorkshopApplication, Long> {
    List<WorkshopApplication> findByWorkshopIdAndUserId(Long workshopId, Long userId);
}
