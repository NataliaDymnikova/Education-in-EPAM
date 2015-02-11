package com.epam.natalia_dymnikova.cities;

import java.util.HashSet;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 *
 * Interface of base of cities.
 */
interface IBaseOfCities {
    public boolean isExist(String name);
    public HashSet<String> getAllCities();
}
