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
public class GalleryJpaService implements GalleryRepository {

    @Autowired
    private GalleryJpaRepository galleryJpaRepository;

    @Autowired
    private ArtistJpaRepository artistJpaRepository;

    @PostConstruct
    @Transactional
    public void resetAutoIncrementOnStartup() {
        galleryJpaRepository.resetAutoIncrement();
    }

    @Override
    public Gallery getGalleryById(int id) {
        return galleryJpaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Gallery> getAllGalleries() {
        return galleryJpaRepository.findAll();
    }

    @Override
    public Gallery saveGallery(Gallery gallery) {
        List<Integer> artistIds = new ArrayList<>();
        for (Artist artist : gallery.getArtists()) {
            artistIds.add(artist.getArtistId());
        }
        List<Artist> artists = artistJpaRepository.findAllById(artistIds);
        if (artistIds.size() != artists.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        gallery.setArtists(artists);

        // Ensure the association is bidirectional
        for (Artist artist : artists) {
            artist.getGalleries().add(gallery);
        }
        Gallery savedGallery = galleryJpaRepository.save(gallery);
        artistJpaRepository.saveAll(artists);

        return savedGallery;
    }

    @Override
    public Gallery updateGallery(int id, Gallery gallery) {
        Gallery existingGallery = galleryJpaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (gallery.getGalleryName() != null) {
            existingGallery.setGalleryName(gallery.getGalleryName());
        }
        if (gallery.getLocation() != null) {
            existingGallery.setLocation(gallery.getLocation());
        }
        if (gallery.getArtists() != null) {
            // Remove the existing gallery from the old artists
            List<Artist> oldArtists = existingGallery.getArtists();
            for (Artist artist : oldArtists) {
                artist.getGalleries().remove(existingGallery);
            }
            artistJpaRepository.saveAll(oldArtists);

            // Add the gallery to the new artists
            List<Integer> newArtistIds = new ArrayList<>();
            for (Artist artist : gallery.getArtists()) {
                newArtistIds.add(artist.getArtistId());
            }
            List<Artist> newArtists = artistJpaRepository.findAllById(newArtistIds);
            if (newArtistIds.size() != newArtists.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            for (Artist artist : newArtists) {
                artist.getGalleries().add(existingGallery);
            }
            artistJpaRepository.saveAll(newArtists);
            existingGallery.setArtists(newArtists);
        }

        return galleryJpaRepository.save(existingGallery);
    }

    @Override
    public void deleteGallery(int id) {
        Gallery gallery = galleryJpaRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Remove the gallery from all associated artists
        List<Artist> artists = gallery.getArtists();
        for (Artist artist : artists) {
            artist.getGalleries().remove(gallery);
        }
        artistJpaRepository.saveAll(artists);

        galleryJpaRepository.deleteById(id);

        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Artist> getGalleryArtists(int id) {
        Gallery gallery = galleryJpaRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return gallery.getArtists();
    }
}
