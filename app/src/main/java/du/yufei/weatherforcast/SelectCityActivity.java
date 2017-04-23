package du.yufei.weatherforcast;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class SelectCityActivity extends AppCompatActivity implements CityListFragment.CitySelectionListener{

    public static String[] mCityName, mCityId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        if(savedInstanceState == null){
            initCity();
        }else{
            mCityName = savedInstanceState.getStringArray(MainActivity.CITYNAMEARRAY);
            mCityId = savedInstanceState.getStringArray(MainActivity.CITYIDARRAY);
        }
        CityListFragment listFragment = new CityListFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(R.id.frame_select_city_activity, listFragment).commit();
    }

    private void initCity(){
        try{
            InputStream input = getAssets().open("json/city.list.json");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            String json = new String(buffer, "UTF-8");
            JSONArray cities = new JSONArray(json);
            mCityName = new String[cities.length()];
            mCityId = new String[cities.length()];
            for(int i = 0; i < cities.length(); i++){
                JSONObject obj = cities.getJSONObject(i);
                mCityName[i] = (obj.getString("name"));
                mCityId[i] = (obj.getString("id"));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void citySelected(String id, String name) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.CITYID,id);
        intent.putExtra(MainActivity.CITYNAME,name);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle state){
        state.putStringArray(MainActivity.CITYNAMEARRAY,mCityName);
        state.putStringArray(MainActivity.CITYIDARRAY,mCityId);
    }
}
