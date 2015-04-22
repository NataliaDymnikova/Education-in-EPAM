package com.epam.natalia_dymnikova.newstry2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.Iterator;
import java.util.List;


public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

		news = new NewsFromJSON(this);
		information = news.getNewNews(current, step);
		adapter = new NewsAdapter(this,information);
		current += step;

		final ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (view.getLastVisiblePosition() >= current - 2) {
					adapter.addAll((news.getNewNews(current, step)));
					adapter.notifyDataSetChanged();
					current += step;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			}
		});

	}

	private NewsAdapter adapter;
	private INews news;
	private int step = 4;
	private int current = 0;
	List<Information> information;

}
