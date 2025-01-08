package com.smen.Services;

import com.smen.Models.MentorRequestStatus;
import com.smen.Repositories.IMentorRequestStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class MentorRequestStatusService extends BaseEntityService<MentorRequestStatus, Long> {
    private final IMentorRequestStatusRepository mentorRequestStatusRepository;

    public MentorRequestStatusService(IMentorRequestStatusRepository mentorRequestStatusRepository) {
        super(mentorRequestStatusRepository);
        this.mentorRequestStatusRepository = mentorRequestStatusRepository;
    }

    public Optional<MentorRequestStatus> getMentorRequestStatusByName(String name) {
        return mentorRequestStatusRepository.findByName(name);
    }

}
