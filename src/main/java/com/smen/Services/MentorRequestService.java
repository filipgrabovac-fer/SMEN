package com.smen.Services;

import com.smen.Models.MentorRequest;
import com.smen.Models.Role;
import com.smen.Models.User;
import com.smen.Repositories.IMentorRequestRepository;
import com.smen.Repositories.IRoleRepository;
import com.smen.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorRequestService extends BaseEntityService<MentorRequest, Long> {
    private final IMentorRequestRepository mentorRequestRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    public MentorRequestService(IMentorRequestRepository mentorRequestRepository,
                                IRoleRepository roleRepository,
                                IUserRepository userRepository) {
        super(mentorRequestRepository);
        this.mentorRequestRepository = mentorRequestRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<MentorRequest> getAllMentorRequests(){
        return mentorRequestRepository.findAll();
    }

    public List<MentorRequest> getMentorsByRequesterId(Long id) {
        return mentorRequestRepository.findByRequesterId(id);
    }

    public List<MentorRequest> getMentorsByReviewerId(Long id) {
        return mentorRequestRepository.findByReviewerId(id);
    }

    public List<MentorRequest> getMentorsByMentorRequestStatusId(Long id) {
        return mentorRequestRepository.findByMentorRequestStatusId(id);
    }

    public MentorRequest approveMentorRequest(Long mentorRequestId) {
        MentorRequest mentorRequest = mentorRequestRepository.findById(mentorRequestId)
                .orElse(null);

        // Fetch and validate the mentor role
        Role mentorRole = roleRepository.findByName("mentor")
                .orElse(null);

        // Update MentorRequest status
        if (mentorRequest != null) {
            mentorRequest.getMentorRequestStatus().setName("approved");

            mentorRequestRepository.save(mentorRequest);

            // Update the user's role
            User requester = mentorRequest.getRequester();
            requester.setRole(mentorRole);
            userRepository.save(requester);
        }

        return mentorRequest;
    }

    public MentorRequest rejectMentorRequest(Long mentorRequestId) {
        MentorRequest mentorRequest = mentorRequestRepository.findById(mentorRequestId)
                .orElse(null);

        // Update MentorRequest status
        if (mentorRequest != null) {
            mentorRequest.getMentorRequestStatus().setName("rejected");

            mentorRequestRepository.save(mentorRequest);
        }

        return mentorRequest;
    }
}
