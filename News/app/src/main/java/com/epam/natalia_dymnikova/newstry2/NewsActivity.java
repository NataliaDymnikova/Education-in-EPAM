package com.epam.natalia_dymnikova.newstry2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

		news = new NewsHardCode(this);
		adapter = new NewsAdapter(this, news.getNewNews(0,20));

		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);
	}

	private NewsAdapter adapter;
	private INews news;

}
