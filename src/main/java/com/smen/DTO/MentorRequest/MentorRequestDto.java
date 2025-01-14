package com.smen.DTO;

import com.smen.Models.MentorRequest;
import com.smen.Models.MentorRequestStatus;
import com.smen.Models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentorRequestDto {
    private String comment;
    private Long requesterId;

    public static MentorRequestDto map(MentorRequest mentorRequest) {
        MentorRequestDto dto = new MentorRequestDto();
        dto.setComment(mentorRequest.getComment());
        dto.setRequesterId(mentorRequest.getRequesterId());
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