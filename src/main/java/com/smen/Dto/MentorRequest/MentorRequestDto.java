package com.smen.Dto;

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
    private Long id;
    private String comment;
    private Long requesterId;
    private Long reviewerId;
    private Long mentorRequestStatusId;

    public static MentorRequestDto map(MentorRequest mentorRequest) {
        MentorRequestDto dto = new MentorRequestDto();
        dto.setId(mentorRequest.getId());
        dto.setComment(mentorRequest.getComment());
        dto.setRequesterId(mentorRequest.getRequester().getId());
        dto.setReviewerId(mentorRequest.getReviewer().getId());
        dto.setMentorRequestStatusId(mentorRequest.getMentorRequestStatus().getId());
        return dto;
    }

    public MentorRequest toEntity(User requester, User reviewer, MentorRequestStatus mentorRequestStatus) {
        MentorRequest mentorRequest = new MentorRequest();
        mentorRequest.setId(this.id);
        mentorRequest.setComment(this.comment);
        mentorRequest.setRequester(requester);
        mentorRequest.setReviewer(reviewer);
        mentorRequest.setMentorRequestStatus(mentorRequestStatus);
        return mentorRequest;
    }
}