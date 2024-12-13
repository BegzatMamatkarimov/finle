package com.example.finle.repository;

import com.example.finle.entity.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT t FROM Photo t WHERE t.imageName LIKE %:title%")
    List<Photo> findByTitleContaining(@Param("title") String title);


}