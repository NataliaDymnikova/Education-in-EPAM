package com.epam.natalia_dymnikova.cities;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Natalia_Dymnikova on 2/11/2015.
 */
public class BaseOfCitiesTest extends ActivityInstrumentationTestCase2<CitiesActivity> {
    public BaseOfCitiesTest() {
        super("com.epam.natalia_dymnikova.cities", CitiesActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mBaseOfCities = new BaseOfCities(getActivity());
    }

    public void testIsExist() throws Exception {
        assertTrue(mBaseOfCities.isExist("aaa"));
        assertFalse(mBaseOfCities.isExist("a"));
    }

    public void testGetAllCities() throws Exception {
        assertEquals(new HashSet(Arrays.asList("aaa", "aab", "aba", "baa")), mBaseOfCities.getAllCities());
    }

    private BaseOfCities mBaseOfCities;
}
