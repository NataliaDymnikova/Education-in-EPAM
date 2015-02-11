package com.epam.natalia_dymnikova.cities;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 *
 * Base of cities use JSON.
 */
public class BaseOfCities implements IBaseOfCities {
    public BaseOfCities(Activity activity) throws IOException, ExceptionCities {
        try {
            JSONObject jsonObject = new JSONObject(read(activity));
            JSONArray array = (JSONArray)jsonObject.get("cities");
            String[] result = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                result[i] = (String)array.get(i);
            }
            mCities = new HashSet<>(Arrays.asList(result));
        } catch (JSONException exc) {
            throw new ExceptionCities("Problem with JSON: " + exc.getMessage());
        }
    }

    /**
     * Check that name is exist.
     * @param name name of city.
     * @return true - if name is exist, false - otherwise.
     */
    public boolean isExist(String name) {
        return mCities.contains(name);
    }

    /**
     * @return all cities.
     */
    public HashSet<String> getAllCities() {
        return mCities;
    }

    private String read(Activity activity) throws IOException {
        InputStream inputStream = activity.getResources().openRawResource(R.raw.cities);
        byte [] buffer = new byte[inputStream.available()];
        //noinspection StatementWithEmptyBody
        while (inputStream.read(buffer) != -1) {
        }

        return new String(buffer);
    }

    private HashSet<String> mCities;
}
