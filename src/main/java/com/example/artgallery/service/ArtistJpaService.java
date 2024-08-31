package com.example.artgallery.service;

import com.example.artgallery.model.*;
import com.example.artgallery.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArtistJpaService implements ArtistRepository {

    @Autowired
    private ArtistJpaRepository artistRepository;

    @Autowired
    private GalleryJpaRepository galleryRepository;

    @Autowired
    private ArtJpaRepository artRepository;

    @PostConstruct
    @Transactional
    public void resetAutoIncrementOnStartup() {
        artistRepository.resetAutoIncrement();
    }

    @Override
    public Artist saveArtist(Artist artist) {
        List<Integer> galleryIds = new ArrayList<>();
        for (Gallery gallery : artist.getGalleries()) {
            galleryIds.add(gallery.getGalleryId());
        }
        List<Gallery> galleries = galleryRepository.findAllById(galleryIds);
        if (galleryIds.size() != galleries.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        artist.setGalleries(galleries);
        return artistRepository.save(artist);

    }

    @Override
    public Artist getArtistById(int artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (!artist.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return artist.get();
    }

    @Override
    public List<Artist> getArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist updateArtist(int id, Artist artist) {

        try {
            Artist exist = artistRepository.findById(id).get();
            if (artist.getArtistName() != null) {
                exist.setArtistName(artist.getArtistName());
            }
            if (artist.getGenre() != null) {
                exist.setGenre(artist.getGenre());
            }

            if (artist.getGalleries() != null && !artist.getGalleries().isEmpty()) {
                List<Integer> newGalleryIds = new ArrayList<>();
                for (Gallery gallery : artist.getGalleries()) {
                    newGalleryIds.add(gallery.getGalleryId());
                }
                List<Gallery> galleries = galleryRepository.findAllById(newGalleryIds);
                if (newGalleryIds.size() != galleries.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                exist.setGalleries(galleries);
            }
            return artistRepository.save(exist);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteArtist(int artistId) {
        try {
            artistRepository.deleteById(artistId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override

    public List<Art> getArtistArts(int id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return artRepository.findAllByArtist(artist);
    }

    @Override

    public List<Gallery> getArtistGalleries(int id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return artist.getGalleries();
    }

}
