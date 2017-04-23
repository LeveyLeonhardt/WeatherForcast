package du.yufei.weatherforcast;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements WeatherData.WeatherDataListener{

    static final int RC_SELECTCITY = 1;
    static final String CITYNAMEARRAY = "du.yufei.WeatherForcast.CITYNAMEARRAY";
    static final String CITYIDARRAY = "du.yufei.WeatherForcast.CITYIDARRAY";
    static final String WEEKWEATHER = "du.yufei.WeatherForcast.WEEKWEATHER";
    static final String CITYNAME = "du.yufei.WeatherForcast.CITYNAME";
    static final String CITYID = "du.yufei.WeatherForcast.CITYID";

    private String mCityId;
    private WeatherData mWeatherData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
            startActivityForResult(intent,RC_SELECTCITY);
        }else{
            mCityId = savedInstanceState.getString(CITYID);
            mWeatherData = new WeatherData(mCityId, this);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_select_city,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        boolean handled;
        switch(item.getItemId()){
            case R.id.menu_change_location:
                handled = true;
                Intent intent = new Intent(MainActivity.this, SelectCityActivity.class);
                startActivityForResult(intent,RC_SELECTCITY);
                break;
            default:
                handled = super.onOptionsItemSelected(item);
                break;
        }
        return handled;
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            mCityId = data.getStringExtra(CITYID);
            ((TextView)findViewById(R.id.text_city_main_activity)).setText(data.getStringExtra(CITYNAME));
            mWeatherData = new WeatherData(mCityId, this);
        }
    }

    public void dataFetched(){
        Bundle bundle = new Bundle();
        Log.d("WeekWeather",mWeatherData.getWeather(1).toString());
        bundle.putStringArray(WEEKWEATHER,mWeatherData.getWeekWeatherString());
        WeekWeatherListFragment listFragment = new WeekWeatherListFragment();
        listFragment.setArguments(bundle);
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.frame_main_activity, listFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle state){
        state.putString(CITYID,mCityId);
    }
}
