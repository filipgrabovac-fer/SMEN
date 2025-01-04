package com.smen.Repositories;

import com.smen.Models.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByWorkshopId(Long workshopId);

    List<Approval> findByUserId(Long userId);
}
