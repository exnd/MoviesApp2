package com.example.moviesapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieSearch extends AppCompatActivity implements RecyclerViewInterface{


    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private MainViewModel3 mainViewModel;
    private ProgressBar progressBar;
    private ListMovieAdapter listMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);


        getSupportActionBar().setElevation(0);


        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel3.class);
        mainViewModel.setWeather("");

        rvMovies = findViewById(R.id.mvsearch);
        progressBar = findViewById(R.id.progressBar);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        listMovieAdapter = new ListMovieAdapter(list,this);
        listMovieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(listMovieAdapter);

        mainViewModel.getWeathers().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> listMovie) {
                if (listMovie != null) {
                    showLoading(false);
                    listMovieAdapter.setData(listMovie);

                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu3, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    mainViewModel.setWeather(query);
                    listMovieAdapter.notifyDataSetChanged();
                    rvMovies.setAdapter(listMovieAdapter);

                    Toast.makeText(MovieSearch.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        if (item.getItemId() == R.id.fav) {
            Intent mIntent = new Intent(MovieSearch.this,Main2Activity.class);
            startActivity(mIntent);
        }

        if (item.getItemId() == R.id.moviesearch) {
            Intent mIntent = new Intent(MovieSearch.this,MainActivity.class);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
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
        DataHelper databaseHandler=new DataHelper(MovieSearch.this);

        Movie newmov5 = new Movie();
        newmov5.setName(list.get(position).getName());
        newmov5.setDescription(list.get(position).getDescription());
        newmov5.setPhoto(list.get(position).getPhoto());

        if (databaseHandler.isAdded(newmov5.getName())){
            Toast.makeText(this, "( "+list.get(position).getName()+" ) Sudah Favorit",Toast.LENGTH_SHORT).show();
        }else{
            databaseHandler.save(newmov5);
            Toast.makeText(this, "( "+list.get(position).getName()+" ) Berhasil Ditambah Ke Favorit",Toast.LENGTH_SHORT).show();
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


        DataHelper databaseHandler=new DataHelper(this);

        databaseHandler.delete(list.get(position));

        Toast.makeText(this, "( "+list.get(position).getName()+" ) Berhasil DiHapus Dari Favorit",Toast.LENGTH_SHORT).show();
        ArrayList<Movie> listMovie=databaseHandler.findAll();
    }
}

