package com.epam.natalia_dymnikova.cities;

import java.util.HashSet;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */

public class Computer implements IPlayer {
    public Computer(Cities base) {
        baseOfCities = base;
    }

    public String MakeMove(String lastString) {
        for (String str : baseOfCities.GetAllCities()) {
            if (str != lastString && str.charAt(0) == lastString.charAt(lastString.length() - 1)) {
                if (!baseOfCities.WasBefore(str))
                    return str;
            }
        }

        return null;
    }

    private Cities baseOfCities;
}
