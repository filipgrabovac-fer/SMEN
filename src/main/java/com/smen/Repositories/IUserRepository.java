package com.smen.Repositories;

import com.smen.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
