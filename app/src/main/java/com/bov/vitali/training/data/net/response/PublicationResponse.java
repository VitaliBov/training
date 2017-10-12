package com.bov.vitali.training.data.net.response;

import com.bov.vitali.training.data.model.Publication;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublicationResponse {

    @SerializedName("data")
    private List<Publication> publications;

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }
}