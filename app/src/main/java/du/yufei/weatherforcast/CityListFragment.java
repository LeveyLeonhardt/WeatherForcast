package du.yufei.weatherforcast;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends ListFragment {

    public interface CitySelectionListener{
        public void citySelected(String id, String name);
    }

    private CitySelectionListener mCitySelectionListener;


    public CityListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,SelectCityActivity.mCityName));
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mCitySelectionListener = (CitySelectionListener) context;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);
        mCitySelectionListener.citySelected(SelectCityActivity.mCityId[position],SelectCityActivity.mCityName[position]);
    }
}
