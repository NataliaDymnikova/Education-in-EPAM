package com.epam.natalia_dymnikova.cities;

/**
 * Created by Natalia_Dymnikova on 2/5/2015.
 *
 * Interface for activity. Can prepare activity for move and have function to finish.
 */
interface IActivity {
    void beginMove(String string);
    public void setDisabled(String string);
}
