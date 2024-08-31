package com.example.artgallery.repository;

import com.example.artgallery.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ArtJpaRepository extends JpaRepository<Art, Integer> {
    List<Art> findAllByArtist(Artist artist);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE art ALTER COLUMN id RESTART WITH 1", nativeQuery = true)
    void resetAutoIncrement();
}