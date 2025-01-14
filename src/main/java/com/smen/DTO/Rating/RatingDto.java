package com.smen.DTO.Rating;

import com.smen.Models.Rating;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Long id;
    private Integer rating;
    private String comment;
    private Long userId;
    private Long workshopId;

    public static RatingDto map(Rating rating) {
        RatingDto dto = new RatingDto();
        dto.setId(rating.getId());
        dto.setRating(rating.getRating());
        dto.setComment(rating.getComment());
        dto.setUserId(rating.getUser() != null ? rating.getUser().getId() : null);
        dto.setWorkshopId(rating.getWorkshop() != null ? rating.getWorkshop().getId() : null);
        return dto;
    }

    public Rating toEntity(User user, Workshop workshop) {
        Rating rating = new Rating();
        rating.setId(this.id);
        rating.setRating(this.rating);
        rating.setComment(this.comment);
        rating.setUser(user);
        rating.setWorkshop(workshop);
        return rating;
    }
}