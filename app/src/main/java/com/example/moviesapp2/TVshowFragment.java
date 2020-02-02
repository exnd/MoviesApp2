package com.example.moviesapp2;


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
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVshowFragment extends Fragment implements RecyclerViewInterface{

    View view;
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private MainViewModel2 mainViewModel2;
    private ProgressBar progressBar;

    public TVshowFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainViewModel2 = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel2.class);

        mainViewModel2.setWeather();


        view = inflater.inflate(R.layout.fragment_home,container,false);

        rvMovies = view.findViewById(R.id.rv_heroes);
        progressBar = view.findViewById(R.id.progressBar);

        final ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);

        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);
        showLoading(true);

        mainViewModel2.getWeathers().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> listMovie2) {
                if (listMovie2 != null) {
                    showLoading(false);
                    listMovieAdapter.setData(listMovie2);

                }
            }
        });

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

        if (databaseHandler.isAdded2(newmov5.getName())){
            Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Sudah Favorit",Toast.LENGTH_SHORT).show();
        }else{
            databaseHandler.save2(newmov5);
            Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Berhasil Ditambah Ke Favorit",Toast.LENGTH_SHORT).show();
        }
        Log.d("reading", "checking all data");
        ArrayList<Movie> listMovie=databaseHandler.findAll2();
        for(Movie b:listMovie){

            Log.d("datamovie", " | JUDUL :"+b.getName()+" | PENULIS:"+b.getDescription()+" | PHOTO: "+b.getPhoto());
            Log.d("Tambah","Tambah 1 bosss");
        }


    }

    @Override
    public void onHapusClick(int position) {
        DataHelper databaseHandler=new DataHelper(getActivity());

        databaseHandler.delete2(list.get(position));

        Toast.makeText(getActivity(), "( "+list.get(position).getName()+" ) Berhasil DiHapus Dari Favorit",Toast.LENGTH_SHORT).show();
        ArrayList<Movie> listMovie=databaseHandler.findAll2();
    }
}
