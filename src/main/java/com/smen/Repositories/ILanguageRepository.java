package com.smen.Repositories;

import com.smen.Models.Language;
import com.smen.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ILanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByName(String name);
}
