package com.example.demolistview;

public class Song {
    private String artistName;
    private String trackName;
    private String previewUrl;
    private String trackId;
    private String artworkUrl100;

    public Song(String artistName, String trackName, String previewUrl, String trackId, String artworkUrl100) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.previewUrl = previewUrl;
        this.trackId = trackId;
        this.artworkUrl100 = artworkUrl100;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }
}
