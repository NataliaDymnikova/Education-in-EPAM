package com.epam.natalia_dymnikova.cities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 *
 */
public class CitiesActivity extends Activity implements IActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        textLastCity = (EditText)findViewById(R.id.textPlayer1);
        textLastCity.setEnabled(false);
        textPlayer = (EditText)findViewById(R.id.textPlayer2);
        buttonOk = (Button)findViewById(R.id.buttonOk);
        textPlayer.setEnabled(true);
        buttonOk.setClickable(true);

        game = new Game(new BaseOfCities(), this, 2);
        buttonOk.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            game.Step(textPlayer.getText().toString());
                                        }
                                    });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void BeginMove(String lastString) {
        textLastCity.setText(lastString);
        textPlayer.setText("");

    }

    public void SetDisabled(String string) {
        textLastCity.setText(string);
        textPlayer.setEnabled(false);
        buttonOk.setClickable(false);
    }

    private EditText textLastCity;
    private EditText textPlayer;
    private Button buttonOk;
    private Game game;
}
