package com.epam.natalia_dymnikova.cities;

import java.util.Set;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */
public interface IBaseOfCities {
    public boolean IsExist(String name);
    public Set<String> GetAllCities();
}
