package com.example.foodprintapps;

public class FoodModel {
    private String FoodName;
    private String ImageUrl;

    public FoodModel(String foodName, String imageUrl) {
        FoodName = foodName;
        ImageUrl = imageUrl;
    }

    public FoodModel() {
    }

    public String getFoodName() {
        return FoodName;
    }

    public FoodModel setFoodName(String foodName) {
        FoodName = foodName;
        return this;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public FoodModel setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
        return this;
    }
}
