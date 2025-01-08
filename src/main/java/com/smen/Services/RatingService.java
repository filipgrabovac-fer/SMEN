package com.smen.Services;

import com.smen.Models.Rating;
import com.smen.Models.Workshop;
import com.smen.Repositories.IRatingRepository;
import com.smen.Repositories.IWorkshopRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService extends BaseEntityService<Rating, Long> {

    private final IRatingRepository ratingRepository;
    private final IWorkshopRepository workshopRepository;

    public RatingService(IRatingRepository ratingRepository, IWorkshopRepository workshopRepository) {
        super(ratingRepository);
        this.ratingRepository = ratingRepository;
        this.workshopRepository = workshopRepository;
    }

    //avg rating svih radionica tog usera
    public Double getAvgUserWorkshopsRating(Long userId) {
        List<Workshop> workshops = workshopRepository.findByUserId(userId);

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


    public List<Rating> getWorkshopRatings(Long workshopId) {
        return ratingRepository.findByWorkshopId(workshopId);
    }

    public List<Rating> getUserRatings(Long userId) {
        return ratingRepository.findByUserId(userId);
    }
}
