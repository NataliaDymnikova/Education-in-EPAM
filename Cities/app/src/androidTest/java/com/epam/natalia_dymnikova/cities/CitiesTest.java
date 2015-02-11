package com.epam.natalia_dymnikova.cities;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.HashSet;

public class CitiesTest extends ActivityInstrumentationTestCase2<CitiesActivity> {
    public CitiesTest() {
        super("com.epam.natalia_dymnikova.cities", CitiesActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mCities = new Cities(new BaseOfCities(getActivity()));
    }

    public void testIsExist() throws Exception {
        assertTrue(mCities.isExist("aba"));
        assertFalse(mCities.isExist("aa"));
    }

    public void testWasBefore() throws Exception {
        assertFalse(mCities.wasBefore("aaa"));
        mCities.putNameCity("aaa");
        assertTrue(mCities.wasBefore("aaa"));
    }

    public void testGetAllCities() throws Exception {
        assertEquals(new HashSet(Arrays.asList("aaa", "aab", "aba", "baa")), mCities.getAllCities());
    }

    private Cities mCities;
}