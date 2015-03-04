package com.epam.natalia_dymnikova.newstry2;

import android.app.Application;
import android.content.res.AssetManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Natalia_Dymnikova on 3/4/2015.
 */
public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        information = new ArrayList<>();
        string = "onCreate";
    }

    public ArrayList<Information> getInformation() throws IOException{
        if (information != null)
            return information;
        InputStream inputStream = getAssets().open("information.json");
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        // Parsing buffer;
        // Add to information;
        return information;
    }

    private String string = "empty";
    private ArrayList<Information> information;
}
