package du.yufei.weatherforcast;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by edwar on 4/23/2017.
 */

public class WeatherData {

    public interface WeatherDataListener{
        public void dataFetched();
    }

    private JSONArray mWeatherDataArray;
    private WeatherDataListener mWeatherDataListener;

    public WeatherData(String id, Context context){
        (new GetData()).execute(id);
        mWeatherDataListener = (WeatherDataListener)context;
    }

    public void setData(JSONArray data){
        mWeatherDataArray = data;
    }

    public Weather getWeather(int day){
        try {
            JSONObject object = mWeatherDataArray.getJSONObject(day);
            Date date = new Date(object.getLong("dt") * 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = dateFormat.format(date);
            String weather = object.getJSONArray("weather").getJSONObject(0).getString("main");
            double min = object.getJSONObject("temp").getDouble("min");
            double max = object.getJSONObject("temp").getDouble("max");
            return new Weather(dateString,weather,min,max);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] getWeekWeatherString(){
        String[] weekWeatherString = new String[7];
        for(int i = 0; i < 7; i++){
            weekWeatherString[i] = getWeather(i).toString();
        }
        return weekWeatherString;
    }

    private class GetData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            String json = null;
            HttpURLConnection connection;
            BufferedReader reader = null;
            try{
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?appid=b5e440224a55338498663b9295399873&cnt=7&units=metric&id="+ URLEncoder.encode(params[0],"UTF-8"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                json = reader.readLine();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(reader != null){
                    try{
                        reader.close();
                    }catch(IOException e){

                    }
                }
            }
            return json;
        }

        @Override
        protected  void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray forcastArray = object.getJSONArray("list");
                setData(forcastArray);
                mWeatherDataListener.dataFetched();
            }catch(Exception e){

            }
        }
    }
}
