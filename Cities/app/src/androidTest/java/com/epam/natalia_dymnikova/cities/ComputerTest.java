package com.epam.natalia_dymnikova.cities;

import android.test.ActivityInstrumentationTestCase2;

import java.util.HashSet;
import java.util.Stack;

public class ComputerTest extends ActivityInstrumentationTestCase2<CitiesActivity> {
    public ComputerTest() {
        super("com.epam.natalia_dymnikova.cities", CitiesActivity.class);
    }

    public void testMakeMove() throws Exception {
        Cities cities = new Cities(new BaseOfCities(getActivity()));
        Computer computer = new Computer(cities);
        assertEquals(null, computer.makeMove("c"));
        assertTrue(cities.isExist(computer.makeMove("aaa")));
        assertFalse(cities.wasBefore(computer.makeMove("aaa")));
        HashSet<String> set = cities.getAllCities();
        for (String name : set) {
            cities.putNameCity(name);
        }
        assertEquals(null, computer.makeMove("aaa"));
    }
}