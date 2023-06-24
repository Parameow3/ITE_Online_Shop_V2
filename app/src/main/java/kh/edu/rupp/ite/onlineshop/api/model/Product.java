package kh.edu.rupp.ite.onlineshop.api.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    private int id;
    private String stu;
    private String name;
    private String description;
    private int price;
    @SerializedName("image-url")
    private String imgUrl;
    private String rating;

    public int getId() {
        return id;
    }

    public String getStu() {
        return stu;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getRating() {
        return rating;
    }


}
