package com.youtube.contentPublisher.contentPublisher.model;

public class AdvertisementMetaData {

    private String advertisementId;
    private String sponsorshipDetail;
    private ContentMetaData content;


    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getSponsorshipDetail() {
        return sponsorshipDetail;
    }

    public void setSponsorshipDetail(String sponsorshipDetail) {
        this.sponsorshipDetail = sponsorshipDetail;
    }

    public ContentMetaData getContent() {
        return content;
    }

    public void setContent(ContentMetaData content) {
        this.content = content;
    }
}
