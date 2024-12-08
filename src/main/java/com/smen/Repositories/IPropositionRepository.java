package com.smen.Repositories;

import com.smen.Models.Proposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPropositionRepository extends JpaRepository<Proposition, Long> {
}
