package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;


import java.util.*;

@Service
public class ArtJpaService implements ArtRepository {

    @Autowired
    private ArtJpaRepository artRepository;

    @Autowired
    private ArtistJpaRepository artistRepository;

    @PostConstruct
    @Transactional
    public void resetAutoIncrementOnStartup() {
        artRepository.resetAutoIncrement();
    }

    @Override
    public Art saveArt(Art art) {

        int artistId = art.getArtist().getArtistId();
        Optional<Artist> existingArtist = artistRepository.findById(artistId);
        if (existingArtist.isPresent()) {
            art.setArtist(existingArtist.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return artRepository.save(art);
    }

    @Override
    public Art getArtById(int artId) {
        Optional<Art> art = artRepository.findById(artId);
        if (!art.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return art.get();
    }

    @Override
    public List<Art> getArts() {
        return artRepository.findAll();
    }

    @Override
    public Art updateArt(int id, Art art) {
        Optional<Art> optionalExist = artRepository.findById(id);

        if (!optionalExist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Art exist = optionalExist.get();

        if (art.getArtTitle() != null) {
            exist.setArtTitle(art.getArtTitle());
        }

        if (art.getArtist() != null) {
            try {
                Artist newArtist = artistRepository.findById(art.getArtist().getArtistId()).get();
                exist.setArtist(newArtist);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }

        return artRepository.save(exist);
    }

    @Override
    public void deleteArt(int artId) {
        if (!artRepository.existsById(artId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            artRepository.deleteById(artId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public Artist getArtArtist(int id) {
        Art art = artRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return art.getArtist();
    }
}
