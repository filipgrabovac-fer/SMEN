package com.smen.Repositories;

import com.smen.Models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ILanguageRepository extends JpaRepository<Language, Long> {

    //kako bi otkrili jezik (1) od usera
    @Query("SELECT l FROM Language l JOIN l.users u WHERE u.id = :userId")
    Optional<Language> findByUserId(@Param("userId") Long userId);
}
