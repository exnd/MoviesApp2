package com.example.favoriteapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private static final String EXTRA_PERSON = "EXTRA PERSON";
    private ListMovieAdapter listMovieAdapter;
    private ContentResolver contentResolver;
    private ProgressBar progressBar;
    private ArrayList<Movie> listMovie = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Favorite App");
        }
        recyclerView = findViewById(R.id.fav_movie);
        progressBar = findViewById(R.id.progressBar);

        contentResolver=getContentResolver();
        setAllData();
        listMovie.addAll(getDatabase());
        recyclerView = findViewById(R.id.fav_movie);
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(listMovie,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listMovieAdapter);

//        HandlerThread handlerProvider = new HandlerThread("DataObserver");
//        handlerProvider.start();
//        Handler handler = new Handler(handlerProvider.getLooper());
//        MainActivity.DataObserver observer = new MainActivity.DataObserver(handler,this);
//        getContentResolver().registerContentObserver(DatabaseContract.FavoriteColumns.CONTENT_URI,true,observer);
//
//        if (savedInstanceState == null){
//            new MainActivity.LoadAsync(this,this).execute();
//        }else {
//            ArrayList<Movie> listnewMovie = savedInstanceState.getParcelableArrayList(EXTRA_PERSON);
//            if (listnewMovie != null){
//               listMovieAdapter = new ListMovieAdapter(listMovie,this);
//            }
//        }

        getSupportActionBar().setElevation(0);
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState){
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(EXTRA_PERSON,listMovieAdapter.getListMovie());
//    }
//
//    public void preExecute(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    @Override
//    public void postExecute(ArrayList<Movie> movieItems){
//        progressBar.setVisibility(View.INVISIBLE);
//        if (movieItems.size() > 0){
//            listMovieAdapter = new ListMovieAdapter(movieItems,this);
//
//        }else{
//            listMovieAdapter = new ListMovieAdapter(new ArrayList<Movie>(),this);
//
//        }
//    }

    public ArrayList<Movie> getDatabase(){

        Log.d("reading", "reading all data");
        ArrayList<Movie> MovieList=setAllData();
        for(Movie b:MovieList){
            Log.d("datamovie", " | JUDUL :"+b.getName()+" | PENULIS:"+b.getDescription()+" | PHOTO: "+b.getPhoto());
            Log.d("Tambah","Tambah 1 bosss");
        }
        return MovieList;
    }

    private ArrayList<Movie> setAllData(){
        ArrayList<Movie> newMovie=new ArrayList<>();

        Cursor cursor = getContentResolver().query(
                DatabaseContract.CONTENT_URI_1,   // CONTENT_URI atau alamat tabel yang diinginkan
                new String[]{"nama","desk","photo"},                        // Column apa saja yang ingin ditampilkan
                null,                    // Klausa kriteria (WHERE…)
                null,                     // Argumen untuk klausa kriteria
                null);


        if(cursor.moveToFirst()){
            do{
                Movie movie=new Movie();
                movie.setName(cursor.getString(0));
                movie.setDescription(cursor.getString(1));
                movie.setPhoto(cursor.getString(2));
                Log.d("_CONTENT_CURSOR2",cursor.getString(0));
                newMovie.add(movie);
                Log.d("_NEWMOVIE",newMovie.get(0).getName());
            }while(cursor.moveToNext());
        }

        return newMovie;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onHapusClick(int position) {

    }


//    public static class DataObserver extends ContentObserver{
//
//        final Context context;
//
//        public DataObserver(Handler handler, Context context) {
//            super(handler);
//            this.context = context;
//        }
//
//        @Override
//        public void onChange(boolean selfChange){
//            super.onChange(selfChange);
//            new MainActivity.LoadAsync(context,(LoadFavoriteCallback) context).execute();
//
//        }
//
//    }

    private void showSnackbarMessage(int message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

//    private static class LoadAsync extends AsyncTask<Void,Void,ArrayList<Movie>> {
//        private final WeakReference<Context> weakFavHelper;
//        private final WeakReference<LoadFavoriteCallback> weakCallback;
//
//        private LoadAsync(Context context, LoadFavoriteCallback callback) {
//            weakFavHelper = new WeakReference<>(context);
//            weakCallback = new WeakReference<>(callback);
//        }
//
//        @Override
//        protected ArrayList<Movie> doInBackground(Void... voids) {
//            Context context = weakFavHelper.get();
//
//            String[] aaa = {"nama"};
//
//
//            Cursor cursor = context.getContentResolver().query(DatabaseContract.CONTENT_URI_1,
//                    null,
//                    null,
//                    null,
//                    null);
//            return DataHelper.mapCursorToArrayList(cursor);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            weakCallback.get().preExecute();
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Movie> movieItems) {
//            super.onPostExecute(movieItems);
//            weakCallback.get().postExecute(movieItems);
//        }
//    }



}
