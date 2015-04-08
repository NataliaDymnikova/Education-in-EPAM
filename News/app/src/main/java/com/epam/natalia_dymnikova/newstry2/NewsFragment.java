package com.epam.natalia_dymnikova.newstry2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalia_Dymnikova on 3/4/2015.
 */
public class NewsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Context context = getActivity().getApplicationContext();
		LinearLayout layout = new LinearLayout(context);
		//layout.setBackgroundColor(Color.BLUE);

		Information information = new Information(null, "1 information");

		TextView text = new TextView(context);
		text.setText(information.getText());
		layout.addView(text, 0);
		TextView text2 = new TextView(context);
		text2.setText(information.getDate().toString());
		layout.addView(text2, 1);
		layout.setPadding(5,5,5,5);


		return layout;
	}

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Information> information = null;
        NewsApplication application = (NewsApplication) getActivity().getApplication();
        try {
            information = application.getInformation();
        } catch (IOException exc) {
            String[] str = new String[1];
            str[0] = "Empty";
            setListAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, str));
        }

        List<String> inf = new ArrayList<>();
        for(Information informat : information) {
            inf.add(informat.getText());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, inf);
        setListAdapter(adapter);
    }*/
}
