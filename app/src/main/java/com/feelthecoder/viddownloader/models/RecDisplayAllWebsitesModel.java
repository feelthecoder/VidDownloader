package com.feelthecoder.viddownloader.models;

public class RecDisplayAllWebsitesModel {


    String websiteurl;
    String websitename;
    String websitestatue;
    String websitesshowtatue;

    String website;
    String packagen;


    public RecDisplayAllWebsitesModel() {
    }

    public RecDisplayAllWebsitesModel(String website,String packagen,String websiteurl, String websitename, String websitestatue, String websitesshowtatue) {
        this.websiteurl = websiteurl;
        this.websitename = websitename;
        this.websitestatue = websitestatue;
        this.websitesshowtatue = websitesshowtatue;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPackagen() {
        return packagen;
    }

    public void setPackagen(String packagen) {
        this.packagen = packagen;
    }

    public String getWebsitesshowtatue() {
        return websitesshowtatue;
    }

    public void setWebsitesshowtatue(String websitesshowtatue) {
        this.websitesshowtatue = websitesshowtatue;
    }

    public String getWebsiteurl() {
        return websiteurl;
    }

    public void setWebsiteurl(String websiteurl) {
        this.websiteurl = websiteurl;
    }

    public String getWebsitename() {
        return websitename;
    }

    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    public String getWebsitestatue() {
        return websitestatue;
    }

    public void setWebsitestatue(String websitestatue) {
        this.websitestatue = websitestatue;
    }
}
