package com.example.marketfinder.entities;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "markets")
public class Market {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private String photoUrl;

    public Market(String name, String location, String photoUrl) {
        this.name = name;
        this.location = location;
        this.photoUrl = photoUrl;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
