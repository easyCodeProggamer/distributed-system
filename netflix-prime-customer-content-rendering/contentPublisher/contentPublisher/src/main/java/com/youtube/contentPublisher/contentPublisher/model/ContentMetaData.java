package com.youtube.contentPublisher.contentPublisher.model;

public class ContentMetaData {

    private Long contentId;
    private String contentType;
    private String contentLanguage;
    private String contentDetails;
    private String contentRelease;
    private String contentRating;
    /*
    Location Example:
        This must be AWS S3 bucket path
        This must be Google Cloud drive path
        or any content-delivery-network(CDN)
    */
    private String contentLocation;





    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public String getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(String contentDetails) {
        this.contentDetails = contentDetails;
    }

    public String getContentRelease() {
        return contentRelease;
    }

    public void setContentRelease(String contentRelease) {
        this.contentRelease = contentRelease;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getContentLocation() {
        return contentLocation;
    }

    public void setContentLocation(String contentLocation) {
        this.contentLocation = contentLocation;
    }
}
