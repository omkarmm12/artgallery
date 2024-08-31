package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.ArtJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtController {

    @Autowired
    private ArtJpaService artService;

    @GetMapping("/galleries/artists/arts")
    public List<Art> getArts() {
        return artService.getArts();
    }

    @PostMapping("/galleries/artists/arts")
    public Art createArt(@RequestBody Art art) {
        return artService.saveArt(art);
    }

    @GetMapping("/galleries/artists/arts/{id}")
    public Art getArtById(@PathVariable("id") int id) {
        return artService.getArtById(id);
    }

    @PutMapping("/galleries/artists/arts/{id}")
    public Art updateArt(@PathVariable("id") int id, @RequestBody Art art) {
        return artService.updateArt(id, art);
    }

    @DeleteMapping("/galleries/artists/arts/{id}")
    public void deleteArt(@PathVariable("id") int id) {
        artService.deleteArt(id);
    }

    @GetMapping("arts/{id}/artist")
    public Artist getArtArtist(@PathVariable("id") int id) {
        return artService.getArtArtist(id);
    }

}
