package com.epam.natalia_dymnikova.cities;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */
public class Game {
    public Game(IBaseOfCities baseOfCities, IActivity activity, int num) {
        cities = new Cities(baseOfCities);
        this.activity = activity;

        computer = new Computer(cities);
        lastCity = cities.GetAllCities().iterator().next();
        cities.PutNameCity(lastCity);
        activity.BeginMove(lastCity);
    }

    /**
     * Make one step by human, than one step by computer.
     * @param newCity - new step by human
     */
    public void Step(String newCity) {
        try {
            if (IsCorrect(newCity))
                cities.PutNameCity(newCity);
        } catch (ExceptionCities exc) {
            activity.SetDisabled(exc.getMessage());
            return;
        } finally {
            lastCity = newCity;
        }
        newCity = computer.MakeMove(lastCity);
        if (newCity == null) {
            activity.SetDisabled("Computer lose");
            return;
        }
        cities.PutNameCity(newCity);
        activity.BeginMove(newCity);
        lastCity = newCity;
    }

    /**
     * Check that name is good or not - exist, doesn't before,
     * correct (name's previous character equals lastCity's last character)
     * @param name new city.
     * @return true if all rules are made.
     * @throws com.epam.natalia_dymnikova.cities.ExceptionCities if rules don't made.
     */
    private boolean IsCorrect(String name) throws ExceptionCities {
        if (cities.WasBefore(name))
            throw new ExceptionCities("Was before");
        if (!cities.IsExist(name))
            throw new ExceptionCities("Don't exist");
        if (lastCity != null && name.charAt(0) != lastCity.charAt(lastCity.length() - 1))
            throw new ExceptionCities("Incorrect");
        return true;
    }

    private String lastCity;
    private IPlayer computer;
    private Cities cities;
    private IActivity activity;
}
