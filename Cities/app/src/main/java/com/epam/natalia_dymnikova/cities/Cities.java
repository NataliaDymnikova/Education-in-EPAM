package com.epam.natalia_dymnikova.cities;


import java.util.HashSet;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 *
 * Keep base of cities and can work with base.
 */
class Cities {
    public Cities(IBaseOfCities baseOfCities) {
        mBaseOfCities = baseOfCities;
        mCitiesBefore = new HashSet<>();
    }

    /**
     * Check that nameCity is exist in base of cities or not.
     * @param nameCity name of city.
     * @return true - if nameOfCity is exist, false - otherwise.
     */
    public boolean isExist(String nameCity) {
        return mBaseOfCities.isExist(nameCity);
    }

    /**
     * Check that nameCity was add with PutNameCity(nameCity) before or not.
     * @param nameCity name of city.
     * @return true - if nameOfCity was before, false - otherwise.
     */
    public boolean wasBefore(String nameCity) {
        return mCitiesBefore.contains(nameCity);
    }

    /**
     * Save nameCity in set.
     * @param nameCity name of city for save.
     */
    public void putNameCity(String nameCity) {
        mCitiesBefore.add(nameCity);
    }

    /**
     * @return all cities in base of cities.
     */
    public HashSet<String> getAllCities() {
        return mBaseOfCities.getAllCities();
    }

    private final IBaseOfCities mBaseOfCities;
    private final HashSet<String> mCitiesBefore;
}
