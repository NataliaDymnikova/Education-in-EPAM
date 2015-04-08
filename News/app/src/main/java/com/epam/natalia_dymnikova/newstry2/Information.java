package com.epam.natalia_dymnikova.newstry2;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

/**
 * Created by Natalia_Dymnikova on 3/4/2015.
 */
public class Information {

    public Information(Bitmap image, String text, Date date) {
        this.image = image;
        this.text = text;
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    private Bitmap image;
    private String text;
    private Date date;

}
