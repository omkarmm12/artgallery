package com.example.artgallery.repository;

import com.example.artgallery.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ArtistJpaRepository extends JpaRepository<Artist, Integer> {

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE artist ALTER COLUMN id RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();

}
