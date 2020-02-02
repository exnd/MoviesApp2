package com.example.moviesapp2;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class MainViewModel2 extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> listMovie2 = new MutableLiveData<>();

    void setWeather() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listItems = new ArrayList<>();

        String url = "";
        if (Locale.getDefault().getLanguage().equals("in")){
            url = "https://api.themoviedb.org/3/discover/tv?api_key=cedfdb517def7da8a93ba61da369b966&language=id";
        }else{
            url = "https://api.themoviedb.org/3/discover/tv?api_key=cedfdb517def7da8a93ba61da369b966&language=en-US";
        }

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray hasilsize = responseObject.getJSONArray("results");

                    for (int i = 0; i < hasilsize.length(); i++) {
                        JSONObject hasil = responseObject.getJSONArray("results").getJSONObject(i);

                        Movie movie = new Movie();
                        String nama2 = hasil.getString("name");
                        String photo = hasil.getString("poster_path");
                        String photo2 = "https://image.tmdb.org/t/p/w500" + photo;
                        String desc2 = hasil.getString("overview");
                        movie.setName(nama2);
                        movie.setDescription(desc2);
                        movie.setPhoto(photo2);
                        listItems.add(movie);
                    }
                    listMovie2.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    LiveData<ArrayList<Movie>> getWeathers() {
        return listMovie2;
    }
}
