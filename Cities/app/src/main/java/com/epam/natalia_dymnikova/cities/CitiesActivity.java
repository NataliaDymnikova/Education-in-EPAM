package com.epam.natalia_dymnikova.cities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Main class of game Cities.
 */
public class CitiesActivity extends Activity implements IActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        mTextLastCity = (EditText)findViewById(R.id.textPlayer1);
        mTextLastCity.setEnabled(false);
        mTextPlayer = (EditText)findViewById(R.id.textPlayer2);
        mButtonOk = (Button)findViewById(R.id.buttonOk);
        mButtonPlayAgain = (Button)findViewById(R.id.buttonPlayAgain);
        mTextPlayer.setEnabled(true);
        mButtonOk.setClickable(true);
        mButtonPlayAgain.setVisibility(View.INVISIBLE);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mGame.step(mTextPlayer.getText().toString());
            }
        });

        mButtonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CitiesActivity.this.onCreate(savedInstanceState);
            }
        });

        try {
            mGame = new Game(new BaseOfCities(this), this);
        } catch (Exception exc) {
            setDisabled("Can't use JSON: " + exc.getMessage());
        }
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

    /**
     * Make necessary actions before beginMove.
     * @param lastString will put to editText - last city.
     */
    public void beginMove(String lastString) {
        mTextLastCity.setText(lastString);
        mTextPlayer.setText("");
    }

    /**
     * Make disabled button and editText - for stop playing.
     * @param string reason to stop playing.
     */
    public void setDisabled(String string) {

        if (!string.contains("try again")) {
            mTextLastCity.setText(string);
            mTextPlayer.setEnabled(false);
            mButtonOk.setClickable(false);
            mButtonPlayAgain.setVisibility(View.VISIBLE);
        }
        else {
            String str = mTextLastCity.getText().toString().replaceAll("\\\n.*", "");
            mTextLastCity.setText(str + "\n"
                    + mTextPlayer.getText() + ": " + string);
        }
    }

    public Game getGame() {
        return mGame;
    }

    private EditText mTextLastCity;
    private EditText mTextPlayer;
    private Button mButtonOk;
    private Button mButtonPlayAgain;
    private Game mGame;
}
