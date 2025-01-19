package com.smen.Repositories;

import com.smen.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IPostRepository extends JpaRepository<Post, Long> {

}
