package com.smen.Repositories;

import com.smen.Models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubjectRepository extends JpaRepository<Subject, Long> {

}
