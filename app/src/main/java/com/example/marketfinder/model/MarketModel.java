package com.example.marketfinder.model;

public class MarketModel{
    private String marketId;
    private String marketName;
    private String marketAddress;
    private double latitude;
    private double longitude;

    public MarketModel() {
        // Construtor vazio necessário para o Firebase
    }

    public MarketModel(String marketId, String marketName, String marketAddress, double latitude, double longitude) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketAddress = marketAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}