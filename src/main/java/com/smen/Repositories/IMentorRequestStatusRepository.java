package com.smen.Repositories;

import com.smen.Models.MentorRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IMentorRequestStatusRepository extends JpaRepository<MentorRequestStatus, Long> {
    Optional<MentorRequestStatus> findByName(String name);
}
