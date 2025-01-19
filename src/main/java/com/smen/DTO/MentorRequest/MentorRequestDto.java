package com.smen.DTO;

import com.smen.Models.MentorRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentorRequestDto {
    private Long id;
    private String comment;
    private Long requesterId;
    private String status;
    private String firstName;
    private String lastName;
    private String email;
    private String createdAt;

    public static MentorRequestDto map(MentorRequest mentorRequest) {
        MentorRequestDto dto = new MentorRequestDto();
        dto.setComment(mentorRequest.getComment());
        dto.setRequesterId(mentorRequest.getRequesterId());
        dto.setId(mentorRequest.getId());
        dto.setCreatedAt(mentorRequest.getCreatedAt().toString());
        return dto;
    }

    public MentorRequest toEntity(Long requesterId, Long reviewerId) {
        MentorRequest mentorRequest = new MentorRequest();
        mentorRequest.setComment(this.comment);
        mentorRequest.setRequesterId(requesterId);
        mentorRequest.setReviewerId(reviewerId);
        mentorRequest.setMentorRequestStatusId(1L);
        return mentorRequest;
    }
}