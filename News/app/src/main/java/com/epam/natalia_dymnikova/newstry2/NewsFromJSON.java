/* =============================================================================
**
**                     Copyright (c) 2015
**                 InterDigital Communications, Inc.
**
**                      All rights reserved.
**
**  The information provided herein is the proprietary and confidential
**  information of InterDigital Communications, Inc. and is made available
**  solely pursuant to the terms of a written agreement.
**
**
** =============================================================================
** =============================================================================
*/
package com.epam.natalia_dymnikova.newstry2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Natalia_Dymnikova on 4/22/2015.
 */
public class NewsFromJSON implements INews {
	private ArrayList<Information> mListInformation;

	public NewsFromJSON(Context context) {
		mListInformation = ((NewsApplication)((Activity) context).getApplication()).getInformation();
	}

	public List<Information> getNewNews(int begin, int number) {
		if (begin > mListInformation.size()) {
			return new ArrayList<>();
		} else {
			return mListInformation.subList(begin, Math.min(begin + number, mListInformation.size()));
		}
	}

}
