package com.epam.natalia_dymnikova.newstry2;

import android.media.Image;

import java.util.Date;

/**
 * Created by Natalia_Dymnikova on 3/4/2015.
 */
public class Information {

    public Information(Image image, String text) {
        this.image = image;
        this.text = text;
        date = new Date();
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    private Image image;
    private String text;
    private Date date;

}
