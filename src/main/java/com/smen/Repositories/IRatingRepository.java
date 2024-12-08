package com.smen.Repositories;

import com.smen.Models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByWorkshopId(Long workshopId);
}
