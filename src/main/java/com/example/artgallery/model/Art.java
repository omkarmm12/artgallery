package com.example.artgallery.model;

import javax.persistence.*;
import com.example.artgallery.model.Artist;

@Entity
@Table(name = "art")
public class Art {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int artId;
    @Column(name = "title")
    private String artTitle;
    @Column(name = "theme")
    private String theme;

    @ManyToOne
    private Artist artist;

    public int getArtId() {
        return artId;
    }

    public void setArtId(int artId) {
        this.artId = artId;
    }

    public String getArtTitle() {
        return artTitle;
    }

    public void setArtTitle(String artTitle) {
        this.artTitle = artTitle;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
