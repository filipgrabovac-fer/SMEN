package com.smen.Services;

import com.smen.DTO.Rating.RatingDto;
import com.smen.Models.Rating;
import com.smen.Models.User;
import com.smen.Models.Workshop;
import com.smen.Repositories.IRatingRepository;
import com.smen.Repositories.IUserRepository;
import com.smen.Repositories.IWorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService extends BaseEntityService<Rating, Long> {

    private final IRatingRepository ratingRepository;
    private final IUserRepository userRepository;
    private final IWorkshopRepository workshopRepository;

    public RatingService(IRatingRepository ratingRepository,
                         IUserRepository userRepository,
                         IWorkshopRepository workshopRepository) {
        super(ratingRepository);
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.workshopRepository = workshopRepository;
    }

    // Create a new rating and return it as DTO
    public RatingDto createRating(RatingDto ratingDto) {
        Optional<User> userOptional = userRepository.findById(ratingDto.getUserId());
        Optional<Workshop> workshopOptional = workshopRepository.findById(ratingDto.getWorkshopId());

        if (!userOptional.isPresent() || !workshopOptional.isPresent()) {
            return null; // Could throw an exception instead
        }

        Rating rating = ratingDto.toEntity(userOptional.get(), workshopOptional.get());
        return RatingDto.map(ratingRepository.save(rating));
    }

    //avg rating svih radionica tog usera
    public Double getAvgUserWorkshopsRating(Long userId) {
        List<Workshop> workshops = workshopRepository.findByOwnerId(userId);

        List<Rating> ratings = new ArrayList<>();

        for (Workshop w : workshops) {
            List<Rating> workshopRatings = ratingRepository.findByWorkshopId(w.getId());
            ratings.addAll(workshopRatings);
        }
        Double rating = 0.0;
        if (ratings.isEmpty())
            return 0.0;
        for (Rating r : ratings) {
            rating += r.getRating();
        }
        return rating / ratings.size();
    }

    //avg rating radionice
    public Double getAvgWorkshopRating(Long workshopId) {
        List<Rating> ratings = ratingRepository.findByWorkshopId(workshopId);
        Double rating = 0.0;
        if (ratings.isEmpty())
            return 0.0;
        for (Rating r : ratings) {
            rating += r.getRating();
        }
        return rating / ratings.size();
    }


    // Get all ratings for a specific workshop as DTOs
    public List<RatingDto> getWorkshopRatings(Long workshopId) {
        return ratingRepository.findByWorkshopId(workshopId)
                .stream()
                .map(RatingDto::map)
                .collect(Collectors.toList());
    }

    // Get all ratings for a specific user as DTOs
    public List<RatingDto> getUserRatings(Long userId) {
        return ratingRepository.findByUserId(userId)
                .stream()
                .map(RatingDto::map)
                .collect(Collectors.toList());
    }
}
