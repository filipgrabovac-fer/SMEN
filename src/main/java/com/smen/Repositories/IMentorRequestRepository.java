package com.smen.Repositories;

import com.smen.Models.MentorRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMentorRequestRepository extends JpaRepository<MentorRequest, Long> {
    List<MentorRequest> findByRequesterId(Long requesterId);

    List<MentorRequest> findByReviewerId(Long reviewerId);

    List<MentorRequest> findByMentorRequestStatusId(Long mentorRequestStatusId);
}
