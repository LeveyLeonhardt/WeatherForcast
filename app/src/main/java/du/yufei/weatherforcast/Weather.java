package du.yufei.weatherforcast;

/**
 * Created by edwar on 4/23/2017.
 */

public class Weather {
    private double mMinTemp, mMaxTemp;
    private String mWeather, mDate;

    public Weather(String date, String weather, double min, double max){
        mDate = date;
        mWeather = weather;
        mMinTemp = min;
        mMaxTemp = max;
    }

    public String getDate(){
        return mDate;
    }

    public String getWeather(){
        return mWeather;
    }

    public double getMin(){
        return mMinTemp;
    }

    public double getMax(){
        return mMaxTemp;
    }

    public String toString(){
        return mDate+": "+mWeather+"; low: "+mMinTemp+" C; high: "+mMaxTemp+" C";
    }
}
