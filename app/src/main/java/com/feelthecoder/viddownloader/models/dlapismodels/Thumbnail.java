package com.feelthecoder.viddownloader.models.dlapismodels;

import androidx.annotation.Keep;

@Keep
public class Thumbnail {
    private String id;
    private String url;

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String value) {
        this.url = value;
    }
}
