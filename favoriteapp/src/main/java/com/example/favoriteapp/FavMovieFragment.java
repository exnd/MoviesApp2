package com.example.favoriteapp;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment implements RecyclerViewInterface {

    public static final String AUTHORITY = "com.example.moviesapp2";
    private static final String SCHEME = "content";
    private static final String TABLE_FAVMOVIE = "t_movie";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVMOVIE)
            .build();


    View view;
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;
    DataHelper dbcenter;
    private Uri uriWithId;
    protected Cursor cursor;

    String[] daftar;


    public FavMovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        DataHelper databaseHandler=new DataHelper(getActivity());
        list.addAll(getDatabase());



        HandlerThread handlerProvider = new HandlerThread("DataObserver");
        handlerProvider.start();
        Handler handler = new Handler(handlerProvider.getLooper());



        view = inflater.inflate(R.layout.fragment_fav_movie,container,false);
        rvMovies = (RecyclerView) view.findViewById(R.id.fav_movie);
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(listMovieAdapter);
        return view;


    }





    public ArrayList<Movie> getDatabase(){
        DataHelper databaseHandler=new DataHelper(getActivity());
        Log.d("reading", "reading all data");
        ArrayList<Movie> listMovie=databaseHandler.findAll();
        for(Movie b:listMovie){
            Log.d("datamovie", " | JUDUL :"+b.getName()+" | PENULIS:"+b.getDescription()+" | PHOTO: "+b.getPhoto());
            Log.d("Tambah","Tambah 1 bosss");
        }
        return listMovie;
    }



    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 500);

        }
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), list.get(position).getName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHapusClick(int position) {
        DataHelper databaseHandler=new DataHelper(getActivity());

        databaseHandler.delete(list.get(position));

        Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Berhasil DiHapus Dari Favorit",Toast.LENGTH_SHORT).show();
        list.clear();
        list.addAll(getDatabase());
        rvMovies = (RecyclerView) view.findViewById(R.id.fav_movie);
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(listMovieAdapter);
    }
}
