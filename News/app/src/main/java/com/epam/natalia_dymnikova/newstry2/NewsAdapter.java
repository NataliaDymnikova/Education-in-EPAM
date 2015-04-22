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
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Natalia_Dymnikova on 4/8/2015.
 */
public class NewsAdapter extends ArrayAdapter<Information> {
	private final Context context;
	private final List<Information> information;

	public NewsAdapter(Context context, List<Information> information) {
		super(context, R.layout.news_item, information);
		this.context = context;
		this.information = information;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.news_item, parent, false);
		TextView textView = (TextView) view.findViewById(R.id.text);
		TextView textViewData = (TextView) view.findViewById(R.id.data);
		ImageView imageView = (ImageView) view.findViewById(R.id.image);

		imageView.setImageBitmap(information.get(position).getImage());
		textView.setText(information.get(position).getText());

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		Date date = information.get(position).getDate();
		textViewData.setText(dateFormat.format(date));

		return view;
	}
}
