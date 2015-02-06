package com.epam.natalia_dymnikova.cities;

import java.util.Arrays;
import java.util.HashSet;

import java.util.Set;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */
public class BaseOfCities implements IBaseOfCities{
    public BaseOfCities() {
        cities = new HashSet<>(Arrays.asList("aaa","aab","baa","aba"));
    }

    public boolean IsExist(String name) {
        return cities.contains(name);
    }

    public Set<String> GetAllCities() {
        return cities;
    }
    private HashSet<String> cities;
}
