package com.epam.natalia_dymnikova.cities;

import java.util.Arrays;
import java.util.HashSet;

import java.util.Set;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */
public class BaseOfCities implements IBaseOfCities{
    public BaseOfCities() {
        mCities = new HashSet<>(Arrays.asList("aaa","aab","baa","aba"));
    }

    /**
     * Check that name is exist.
     * @param name
     * @return true - if name is exist, false - otherwise.
     */
    public boolean isExist(String name) {
        return mCities.contains(name);
    }

    /**
     * @return all cities.
     */
    public Set<String> getAllCities() {
        return mCities;
    }

    private HashSet<String> mCities;
}
