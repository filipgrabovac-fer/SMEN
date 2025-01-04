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

    public MentorRequest approveMentorRequest(Long mentorRequestId) {
        MentorRequest mentorRequest = mentorRequestRepository.findById(mentorRequestId)
                .orElseThrow(() -> new IllegalStateException("MentorRequest with ID " + mentorRequestId + " not found"));

        // Fetch and validate the mentor role
        Role mentorRole = roleRepository.findByName("mentor")
                .orElseThrow(() -> new IllegalStateException("Role 'mentor' not found"));

        // Update MentorRequest status
        mentorRequest.getMentorRequestStatus().setName("approved");
        mentorRequestRepository.save(mentorRequest);

        // Update the user's role
        User requester = mentorRequest.getRequester();
        requester.setRole(mentorRole);
        userRepository.save(requester);

        return mentorRequest;
    }

    public MentorRequest rejectMentorRequest(Long mentorRequestId) {
        MentorRequest mentorRequest = mentorRequestRepository.findById(mentorRequestId)
                .orElseThrow(() -> new IllegalStateException("MentorRequest with ID " + mentorRequestId + " not found"));

        // Update MentorRequest status
        mentorRequest.getMentorRequestStatus().setName("rejected");
        mentorRequestRepository.save(mentorRequest);

        return mentorRequest;
    }
}
