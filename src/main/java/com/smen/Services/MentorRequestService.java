package com.smen.Services;

import com.smen.DTO.MentorRequestDto;
import com.smen.Models.MentorRequest;
import com.smen.Models.MentorRequestStatus;
import com.smen.Models.Role;
import com.smen.Models.User;
import com.smen.Repositories.IMentorRequestRepository;
import com.smen.Repositories.IMentorRequestStatusRepository;
import com.smen.Repositories.IRoleRepository;
import com.smen.Repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorRequestService extends BaseEntityService<MentorRequest, Long> {
    private final IMentorRequestRepository mentorRequestRepository;
    private final IMentorRequestStatusRepository mentorRequestStatusRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    public MentorRequestService(IMentorRequestRepository mentorRequestRepository,
                                IRoleRepository roleRepository,
                                IUserRepository userRepository,
                                IMentorRequestStatusRepository mentorRequestStatusRepository) {
        super(mentorRequestRepository);
        this.mentorRequestRepository = mentorRequestRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.mentorRequestStatusRepository = mentorRequestStatusRepository;
    }

    public List<MentorRequestDto> getAllMentorRequests() {
        return mentorRequestRepository.findAll()
                .stream()
                .map(MentorRequestDto::map)
                .collect(Collectors.toList());
    }

    public List<MentorRequestDto> getMentorsByRequesterId(Long id) {
        return mentorRequestRepository.findByRequesterId(id)
                .stream()
                .map(MentorRequestDto::map)
                .collect(Collectors.toList());
    }

    public List<MentorRequestDto> getMentorsByReviewerId(Long id) {
        return mentorRequestRepository.findByReviewerId(id)
                .stream()
                .map(MentorRequestDto::map)
                .collect(Collectors.toList());
    }

    public List<MentorRequestDto> getMentorsByMentorRequestStatusId(Long id) {
        return mentorRequestRepository.findByMentorRequestStatusId(id)
                .stream()
                .map(MentorRequestDto::map)
                .collect(Collectors.toList());
    }

    public MentorRequestDto approveMentorRequest(Long mentorRequestId) {
        Optional<MentorRequest> mentorRequestOptional = mentorRequestRepository.findById(mentorRequestId);

        if (!mentorRequestOptional.isPresent()) {
            return null;
        }

        MentorRequest mentorRequest = mentorRequestOptional.get();

        Role mentorRole = roleRepository.findByName("mentor")
                .orElse(null);
        if (mentorRole == null)
            return null;

        mentorRequest.setMentorRequestStatusId(2L);
        MentorRequest updatedRequest = mentorRequestRepository.save(mentorRequest);

        Long requesterId = updatedRequest.getRequesterId();
        User requester = userRepository.findById(requesterId).orElse(null);

        if (requester != null) {
            requester.setRole(mentorRole);
            userRepository.save(requester);
        }

        return MentorRequestDto.map(updatedRequest);
    }

    public MentorRequestDto rejectMentorRequest(Long mentorRequestId) {
        Optional<MentorRequest> mentorRequestOptional = mentorRequestRepository.findById(mentorRequestId);

        if (!mentorRequestOptional.isPresent()) {
            return null;
        }

        MentorRequest mentorRequest = mentorRequestOptional.get();

        mentorRequest.setMentorRequestStatusId(3L);
        return MentorRequestDto.map(mentorRequestRepository.save(mentorRequest));
    }

    public MentorRequestDto createMentorRequest(MentorRequestDto requestDto) {
        Optional<User> requesterOptional = userRepository.findById(requestDto.getRequesterId());

        if (requesterOptional.isEmpty()) {
            return null;
        }

        MentorRequest mentorRequest = requestDto.toEntity(requestDto.getRequesterId(), null);

        return MentorRequestDto.map(mentorRequestRepository.save(mentorRequest));
    }
}