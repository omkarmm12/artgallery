package com.example.artgallery.controller;

import com.example.artgallery.model.*;
import com.example.artgallery.service.GalleryJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GalleryController {

    @Autowired
    private GalleryJpaService galleryService;

    @GetMapping("/galleries")
    public List<Gallery> getGalleries() {
        return galleryService.getAllGalleries();
    }

    @PostMapping("/galleries")
    public Gallery createGallery(@RequestBody Gallery gallery) {
        return galleryService.saveGallery(gallery);
    }

    @GetMapping("/galleries/{id}")
    public Gallery getGalleryById(@PathVariable("id") int id) {
        return galleryService.getGalleryById(id);
    }

    @PutMapping("/galleries/{id}")
    public Gallery updateGallery(@PathVariable("id") int id, @RequestBody Gallery gallery) {
        return galleryService.updateGallery(id, gallery);
    }

    @DeleteMapping("/galleries/{id}")
    public void deleteGallery(@PathVariable("id") int id) {
        galleryService.deleteGallery(id);
    }

    @GetMapping("/galleries/{id}/artists")
    public List<Artist> getGalleryArts(@PathVariable("id") int id) {
        return galleryService.getGalleryArtists(id);
    }
}
