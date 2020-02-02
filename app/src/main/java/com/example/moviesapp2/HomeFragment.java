package com.example.moviesapp2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;



public class HomeFragment extends Fragment implements RecyclerViewInterface{

    View view;
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;



    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mainViewModel.setWeather();


        view = inflater.inflate(R.layout.fragment_home,container,false);
        rvMovies = view.findViewById(R.id.rv_heroes);
        progressBar = view.findViewById(R.id.progressBar);

        final ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);



        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);


        showLoading(true);

        mainViewModel.getWeathers().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> listMovie) {
                if (listMovie != null) {
                    showLoading(false);
                    listMovieAdapter.setData(listMovie);

                }
            }
        });
        setHasOptionsMenu(true);

        return view;


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
        DataHelper databaseHandler=new DataHelper(getActivity());

        Movie newmov5 = new Movie();
        newmov5.setName(list.get(position).getName());
        newmov5.setDescription(list.get(position).getDescription());
        newmov5.setPhoto(list.get(position).getPhoto());

        if (databaseHandler.isAdded(newmov5.getName())){
            Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Sudah Favorit",Toast.LENGTH_SHORT).show();
        }else{
            databaseHandler.save(newmov5);
            Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Berhasil Ditambah Ke Favorit",Toast.LENGTH_SHORT).show();
        }
        Log.d("reading", "checking all data");
        ArrayList<Movie> listMovie=databaseHandler.findAll();
        for(Movie b:listMovie){

            Log.d("datamovie", " | JUDUL :"+b.getName()+" | PENULIS:"+b.getDescription()+" | PHOTO: "+b.getPhoto());
            Log.d("Tambah","Tambah 1 bosss");
        }


    }

    @Override
    public void onHapusClick(int position) {


        DataHelper databaseHandler=new DataHelper(getActivity());

        databaseHandler.delete(list.get(position));

        Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Berhasil DiHapus Dari Favorit",Toast.LENGTH_SHORT).show();
        ArrayList<Movie> listMovie=databaseHandler.findAll();
    }
}
