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
    private final MentorRequestStatusService mentorRequestStatusService;

    public MentorRequestService(IMentorRequestRepository mentorRequestRepository,
                                IRoleRepository roleRepository,
                                IUserRepository userRepository,
                                IMentorRequestStatusRepository mentorRequestStatusRepository, MentorRequestStatusService mentorRequestStatusService) {
        super(mentorRequestRepository);
        this.mentorRequestRepository = mentorRequestRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.mentorRequestStatusRepository = mentorRequestStatusRepository;
        this.mentorRequestStatusService = mentorRequestStatusService;
    }

    public List<MentorRequestDto> getAllMentorRequests() {
        return mentorRequestRepository.findAll()
                .stream()
                .map(mentorRequest->{
                    if (userRepository.findById(mentorRequest.getRequesterId()).isEmpty()) return null;

                    User user = userRepository.findById(mentorRequest.getRequesterId()).get();

                    MentorRequestDto mentorRequestDto = new MentorRequestDto();
                    mentorRequestDto.setRequesterId(mentorRequestDto.getRequesterId());
                    mentorRequestDto.setStatus(mentorRequestStatusService.getById(mentorRequest.getMentorRequestStatusId()).get().getName());
                    mentorRequestDto.setComment(mentorRequest.getComment());
                    mentorRequestDto.setId(mentorRequest.getId());
                    mentorRequestDto.setCreatedAt(mentorRequest.getCreatedAt().toString());
                    mentorRequestDto.setEmail(user.getEmail());
                    mentorRequestDto.setFirstName(user.getFirstName());
                    mentorRequestDto.setLastName(user.getLastName());
                    return mentorRequestDto;
                })
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

        if (mentorRequestOptional.isEmpty()) {
            return null;
        }

        MentorRequest mentorRequest = mentorRequestOptional.get();

        mentorRequest.setMentorRequestStatusId(2L);
        MentorRequest updatedRequest = mentorRequestRepository.save(mentorRequest);


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