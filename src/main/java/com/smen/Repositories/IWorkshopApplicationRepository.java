package com.smen.Repositories;

import com.smen.Models.Post;
import com.smen.Models.WorkshopApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkshopApplicationRepository  extends JpaRepository<WorkshopApplication, Long> {
}
