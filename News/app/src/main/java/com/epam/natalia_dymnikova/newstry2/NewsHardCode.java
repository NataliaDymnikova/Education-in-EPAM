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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Natalia_Dymnikova on 4/8/2015.
 */
public class NewsHardCode implements INews {

	private Context context;

	public NewsHardCode(Context context) {
		this.context = context;
	}

	public List<Information> getNewNews(int begin, int number) {
		if (begin > 20)
			return null;
		ArrayList<Information> listInformation = new ArrayList<>();
		for (int i = begin; i < begin + number && i < 20; i++) {
			listInformation.add(new Information(
					((BitmapDrawable)context.getResources().getDrawable(R.drawable.picture)).getBitmap(),
					i+1 + ") alalal lalalalalala llalalal!!!a lalalala", new Date()));
		}
		return listInformation;
	}
}
