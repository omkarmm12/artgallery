package com.example.artgallery.repository;

import com.example.artgallery.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface GalleryJpaRepository extends JpaRepository<Gallery, Integer> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE gallery ALTER COLUMN id RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();
}