package com.example.artgallery.repository;

import java.util.List;
import com.example.artgallery.model.*;

public interface ArtistRepository {

    List<Artist> getArtists();

    Artist getArtistById(int id);

    Artist saveArtist(Artist artist);

    Artist updateArtist(int id, Artist artist);

    void deleteArtist(int id);

    List<Gallery> getArtistGalleries(int id);

    List<Art> getArtistArts(int id);
}