package com.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {
    @JsonProperty("RCP_NM")
    private String name;

    @JsonProperty("ATT_FILE_NO_MAIN")
    private String imageUrl;

    @JsonProperty("RCP_PARTS_DTLS")
    private String details;

    @JsonProperty("INFO_ENG")
    private String calories;

    @JsonProperty("INFO_CAR")
    private String carbohydrates;

    @JsonProperty("INFO_PRO")
    private String protein;

    @JsonProperty("INFO_FAT")
    private String fat;

    @JsonProperty("INFO_NA")
    private String sodium;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }
}
