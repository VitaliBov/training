package com.bov.vitali.training.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Film {

    @SerializedName("poster_path")
    private String posterPath;
    private Boolean adult;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();
    private Integer id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        if (getPosterPath() != null ? !getPosterPath().equals(film.getPosterPath()) : film.getPosterPath() != null)
            return false;
        if (getAdult() != null ? !getAdult().equals(film.getAdult()) : film.getAdult() != null)
            return false;
        if (getOverview() != null ? !getOverview().equals(film.getOverview()) : film.getOverview() != null)
            return false;
        if (getReleaseDate() != null ? !getReleaseDate().equals(film.getReleaseDate()) : film.getReleaseDate() != null)
            return false;
        if (getGenreIds() != null ? !getGenreIds().equals(film.getGenreIds()) : film.getGenreIds() != null)
            return false;
        if (getId() != null ? !getId().equals(film.getId()) : film.getId() != null) return false;
        if (getOriginalTitle() != null ? !getOriginalTitle().equals(film.getOriginalTitle()) : film.getOriginalTitle() != null)
            return false;
        if (getOriginalLanguage() != null ? !getOriginalLanguage().equals(film.getOriginalLanguage()) : film.getOriginalLanguage() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(film.getTitle()) : film.getTitle() != null)
            return false;
        if (getBackdropPath() != null ? !getBackdropPath().equals(film.getBackdropPath()) : film.getBackdropPath() != null)
            return false;
        if (getPopularity() != null ? !getPopularity().equals(film.getPopularity()) : film.getPopularity() != null)
            return false;
        if (getVoteCount() != null ? !getVoteCount().equals(film.getVoteCount()) : film.getVoteCount() != null)
            return false;
        if (getVideo() != null ? !getVideo().equals(film.getVideo()) : film.getVideo() != null)
            return false;
        return getVoteAverage() != null ? getVoteAverage().equals(film.getVoteAverage()) : film.getVoteAverage() == null;

    }

    @Override
    public int hashCode() {
        int result = getPosterPath() != null ? getPosterPath().hashCode() : 0;
        result = 31 * result + (getAdult() != null ? getAdult().hashCode() : 0);
        result = 31 * result + (getOverview() != null ? getOverview().hashCode() : 0);
        result = 31 * result + (getReleaseDate() != null ? getReleaseDate().hashCode() : 0);
        result = 31 * result + (getGenreIds() != null ? getGenreIds().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getOriginalTitle() != null ? getOriginalTitle().hashCode() : 0);
        result = 31 * result + (getOriginalLanguage() != null ? getOriginalLanguage().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getBackdropPath() != null ? getBackdropPath().hashCode() : 0);
        result = 31 * result + (getPopularity() != null ? getPopularity().hashCode() : 0);
        result = 31 * result + (getVoteCount() != null ? getVoteCount().hashCode() : 0);
        result = 31 * result + (getVideo() != null ? getVideo().hashCode() : 0);
        result = 31 * result + (getVoteAverage() != null ? getVoteAverage().hashCode() : 0);
        return result;
    }
}