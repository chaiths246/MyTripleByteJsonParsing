package com.example.chaithra.mytriplebyte;

/**
 * Created by chaithra on 3/12/18.
 */

public class Meow {
    private String date;
    private String image;
    private String title;
    private String description;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Meow(String date, String image, String title, String description)
    {
        this.date=date;
        this.image=image;
        this.title=title;
        this.description=description;

    }
}
