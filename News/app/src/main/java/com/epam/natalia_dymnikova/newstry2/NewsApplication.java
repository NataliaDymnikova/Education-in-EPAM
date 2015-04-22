package com.epam.natalia_dymnikova.newstry2;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Natalia_Dymnikova on 3/4/2015.
 */
public class NewsApplication extends Application {

	private ArrayList<Information> mListInformation;

    @Override
    public void onCreate() {
        super.onCreate();

		createInformation();
    }

    public ArrayList<Information> getInformation() {
        return mListInformation;
    }

	private void createInformation() {
		try {
			JSONObject jsonObject = new JSONObject(readFromJSON());
			JSONArray array = jsonObject.getJSONArray("news");
			mListInformation = new ArrayList<>();
			DateFormat format = new SimpleDateFormat("d M yyyy");
			for (int i = 0; i < array.length(); i++) {
				JSONArray jsonInform = array.getJSONArray(i);

				Bitmap bitmap;
				switch (jsonInform.getString(0)) {
					case "picture.png":
						bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.picture)).getBitmap();
						break;
					default:
						bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.error)).getBitmap();
				}

				mListInformation.add(new Information(
						bitmap, jsonInform.getString(1), format.parse(jsonInform.getString(2))));
			}

		} catch (JSONException exc) {
			String str = exc.getMessage();
		} catch (ParseException exc) {
			String str = exc.getMessage();
		} catch (IOException exc) {
			String str = exc.getMessage();
		}
	}

	private String readFromJSON() throws IOException {
		InputStream inputStream = getAssets().open("news");
		byte [] buffer = new byte[inputStream.available()];
		while (inputStream.read(buffer) != -1) {
		}
		inputStream.close();
		return new String(buffer);
	}
}
