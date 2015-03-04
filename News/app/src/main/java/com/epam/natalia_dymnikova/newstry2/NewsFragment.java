package com.epam.natalia_dymnikova.newstry2;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natalia_Dymnikova on 3/4/2015.
 */
public class NewsFragment extends ListFragment {
    @Override
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
    }
}
