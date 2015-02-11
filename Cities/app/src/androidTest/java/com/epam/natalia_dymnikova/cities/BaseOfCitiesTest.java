package com.epam.natalia_dymnikova.cities;

import android.test.ActivityInstrumentationTestCase2;

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
