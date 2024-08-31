package com.example.artgallery.repository;

import java.util.List;
import com.example.artgallery.model.*;

public interface GalleryRepository {

    List<Gallery> getAllGalleries();

    Gallery getGalleryById(int id);

    Gallery saveGallery(Gallery gallery);

    Gallery updateGallery(int id, Gallery gallery);

    void deleteGallery(int id);

    List<Artist> getGalleryArtists(int id);
}
