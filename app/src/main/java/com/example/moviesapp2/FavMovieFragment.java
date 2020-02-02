package com.example.moviesapp2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
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


    View view;
    private RecyclerView rvMovies;
    private ArrayList<Movie> list = new ArrayList<>();
    private MainViewModel mainViewModel;
    private SQLiteDatabase sqLiteDatabase;
    private ProgressBar progressBar;
    DataHelper dbcenter;
    protected Cursor cursor;
    String[] daftar;


    public FavMovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DataHelper databaseHandler=new DataHelper(getActivity());
        list.addAll(getDatabase());
        getDatabase2();
        setAllData();




        view = inflater.inflate(R.layout.fragment_home,container,false);
        rvMovies = (RecyclerView) view.findViewById(R.id.rv_heroes);
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

    public ArrayList<Movie> getDatabase2(){
        DataHelper databaseHandler=new DataHelper(getActivity());
        Log.d("reading", "reading all data");
        ArrayList<Movie> listMovie=databaseHandler.getCursorData();
        for(Movie b:listMovie){
            Log.d("datamovie", " | JUDUL :"+b.getName()+" | PENULIS:"+b.getDescription()+" | PHOTO: "+b.getPhoto());
            Log.d("Tambah","Tambah 1 bosss");
        }
        return listMovie;
    }


    private void setAllData(){
        ArrayList<Movie> listMovie=new ArrayList<Movie>();

        Cursor cursor = getContext().getContentResolver().query(
                DatabaseContract.CONTENT_URI_1,   // CONTENT_URI atau alamat tabel yang diinginkan
                new String[]{"nama","desk","photo"},                        // Column apa saja yang ingin ditampilkan
                null,                    // Klausa kriteria (WHERE…)
                null,                     // Argumen untuk klausa kriteria
                null);

        ArrayList<Movie> listnewmovie = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Movie movie=new Movie();
                movie.setName(cursor.getString(0));
                movie.setDescription(cursor.getString(1));
                movie.setPhoto(cursor.getString(1));
                Log.d("_CONTENT_CURSOR2",cursor.getString(0));
                listMovie.add(movie);
            }while(cursor.moveToNext());
        }
        cursor.close();
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
        rvMovies = (RecyclerView) view.findViewById(R.id.rv_heroes);
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(list,this);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(listMovieAdapter);
    }
}
