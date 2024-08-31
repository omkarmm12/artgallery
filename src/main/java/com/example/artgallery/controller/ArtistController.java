package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.ArtistJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {

    @Autowired
    private ArtistJpaService artistService;

    @GetMapping("/galleries/artists")
    public List<Artist> getArtists() {
        return artistService.getArtists();
    }

    @PostMapping("/galleries/artists")
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.saveArtist(artist);
    }

    @GetMapping("/galleries/artists/{id}")
    public Artist getArtistById(@PathVariable("id") int id) {
        return artistService.getArtistById(id);
    }

    @PutMapping("/galleries/artists/{id}")
    public Artist updateArtist(@PathVariable("id") int id, @RequestBody Artist artist) {
        return artistService.updateArtist(id, artist);
    }

    @DeleteMapping("/galleries/artists/{id}")
    public void deleteArtist(@PathVariable("id") int id) {
        artistService.deleteArtist(id);
    }

    @GetMapping("/artists/{id}/arts")
    public List<Art> getArtistArts(@PathVariable("id") int id) {
        return artistService.getArtistArts(id);
    }

    @GetMapping("/artists/{id}/galleries")
    public List<Art> getArtistGalleries(@PathVariable("id") int id) {
        return artistService.getArtistGalleries(id);
    }

}
