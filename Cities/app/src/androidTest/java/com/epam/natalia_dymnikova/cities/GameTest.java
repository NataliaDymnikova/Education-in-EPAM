package com.epam.natalia_dymnikova.cities;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class GameTest  extends ActivityInstrumentationTestCase2<CitiesActivity> {
    public GameTest() {
        super("com.epam.natalia_dymnikova.cities", CitiesActivity.class);
    }

    public void testStep() throws Exception {
        Game game = getActivity().getGame();
        game.step("aaa");
        assertTrue(true);
    }
}