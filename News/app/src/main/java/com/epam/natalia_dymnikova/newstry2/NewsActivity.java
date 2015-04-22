package com.epam.natalia_dymnikova.newstry2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

		news = new NewsFromJSON(this);
		adapter = new NewsAdapter(this, news.getNewNews(0,20));

		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}

	private NewsAdapter adapter;
	private INews news;

}
