package com.epam.natalia_dymnikova.cities;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 */
public class Game {
    public Game(IBaseOfCities baseOfCities, IActivity activity, int num) {
        mCities = new Cities(baseOfCities);
        mActivity = activity;

        mComputer = new Computer(mCities);
        mLastCity = mCities.getAllCities().iterator().next();
        mCities.putNameCity(mLastCity);
        mActivity.beginMove(mLastCity);
    }

    /**
     * Make one step by human, than one step by computer.
     * @param newCity - new step by human
     */
    public void step(String newCity) {
        try {
            if (isCorrect(newCity))
                mCities.putNameCity(newCity);
        } catch (ExceptionCities exc) {
            mActivity.setDisabled(exc.getMessage());
            return;
        } finally {
            mLastCity = newCity;
        }
        newCity = mComputer.makeMove(mLastCity);
        if (newCity == null) {
            mActivity.setDisabled("Computer lose");
            return;
        }
        mCities.putNameCity(newCity);
        mActivity.beginMove(newCity);
        mLastCity = newCity;
    }

    /**
     * Check that name is good or not - exist, doesn't before,
     * correct (name's previous character equals lastCity's last character)
     * @param name new city.
     * @return true if all rules are made.
     * @throws com.epam.natalia_dymnikova.cities.ExceptionCities if rules don't made.
     */
    private boolean isCorrect(String name) throws ExceptionCities {
        if (mCities.wasBefore(name))
            throw new ExceptionCities("Was before");
        if (!mCities.isExist(name))
            throw new ExceptionCities("Don't exist");
        if (mLastCity != null && name.charAt(0) != mLastCity.charAt(mLastCity.length() - 1))
            throw new ExceptionCities("Incorrect");
        return true;
    }

    private String mLastCity;
    private IPlayer mComputer;
    private Cities mCities;
    private IActivity mActivity;
}
