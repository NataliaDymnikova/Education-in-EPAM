package com.epam.natalia_dymnikova.cities;


import java.util.HashSet;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */
public class Cities {
    public Cities(IBaseOfCities baseOfCities) {
        this.baseOfCities = baseOfCities;
        citiesBefore = new HashSet<>();
    }

    public boolean IsExist(String nameCity) {
        return baseOfCities.IsExist(nameCity);
    }

    public boolean WasBefore(String nameCity) {
        return citiesBefore.contains(nameCity);
    }

    public void PutNameCity(String nameCity) {
        citiesBefore.add(nameCity);
    }

    public HashSet<String> GetAllCities() {
        return (HashSet)baseOfCities.GetAllCities();
    }

    private IBaseOfCities baseOfCities;
    private HashSet<String> citiesBefore;
}
