package com.example.moviesapp2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment implements RecyclerViewInterface{

    View view;
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;
    DataHelper dbcenter;
    protected Cursor cursor;
    String[] daftar;


    public FavTvFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DataHelper databaseHandler=new DataHelper(getActivity());
        list.addAll(getDatabase());

        view = inflater.inflate(R.layout.fragment_home,container,false);
        rvMovies = (RecyclerView) view.findViewById(R.id.rv_heroes);
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(listMovieAdapter);
        return view;



    }


    public ArrayList<Movie> getDatabase(){
        DataHelper databaseHandler = new DataHelper(getActivity());
        Log.d("reading", "reading all data");
        ArrayList<Movie> listMovie=databaseHandler.findAll2();
        for(Movie b:listMovie){
            Log.d("datamoviefavtv", " | JUDUL :"+b.getName()+" | PENULIS:"+b.getDescription()+" | PHOTO: "+b.getPhoto());
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

        databaseHandler.delete2(list.get(position));

        Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Berhasil DiHapus Dari Favorit",Toast.LENGTH_SHORT).show();
        list.clear();
        list.addAll(getDatabase());
        rvMovies = (RecyclerView) view.findViewById(R.id.rv_heroes);
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(listMovieAdapter);
    }
}
