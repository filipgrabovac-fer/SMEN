package com.smen.Repositories;

import com.smen.Models.MentorRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMentorRequestStatusRepository extends JpaRepository<MentorRequestStatus, Long> {
    MentorRequestStatus findByName(String name);
}
