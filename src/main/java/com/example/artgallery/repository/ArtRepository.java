package com.example.artgallery.repository;

import java.util.List;
import com.example.artgallery.model.*;

public interface ArtRepository {

    List<Art> getArts();

    Art getArtById(int id);

    Art saveArt(Art art);

    Art updateArt(int id, Art art);

    void deleteArt(int id);

    Artist getArtArtist(int id);
}
