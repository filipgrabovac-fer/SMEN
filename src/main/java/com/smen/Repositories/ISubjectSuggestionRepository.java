package com.smen.Repositories;

import com.smen.Models.SubjectSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubjectSuggestionRepository extends JpaRepository<SubjectSuggestion, Long> {
}
