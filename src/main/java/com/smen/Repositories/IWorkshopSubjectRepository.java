package com.smen.Repositories;

import com.smen.Models.WorkshopSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWorkshopSubjectRepository extends JpaRepository<WorkshopSubject, Long> {
    List<WorkshopSubject> findByWorkshopId(Long workshopId);

    List<WorkshopSubject> findBySubjectId(Long subjectId);
}
