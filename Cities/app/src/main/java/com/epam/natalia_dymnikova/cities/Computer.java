package com.epam.natalia_dymnikova.cities;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 *
 * Player imitation.
 */

public class Computer implements IPlayer {
    public Computer(Cities base) {
        mBaseOfCities = base;
    }

    /**
     * Make one move.
     * @param lastString last name of city.
     * @return new string with first character equals lastString's last character.
     * Null - if don't exist.
     */
    public String makeMove(String lastString) {
        for (String str : mBaseOfCities.getAllCities()) {
            if (!str.equals(lastString) && str.charAt(0) == lastString.charAt(lastString.length() - 1)) {
                if (!mBaseOfCities.wasBefore(str))
                    return str;
            }
        }
        return null;
    }

    private final Cities mBaseOfCities;
}
