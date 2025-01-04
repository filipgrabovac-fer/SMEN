package com.smen.Repositories;

import com.smen.Models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByTitle(String title);
}
