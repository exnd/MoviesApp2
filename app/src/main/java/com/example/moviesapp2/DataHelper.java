package com.example.moviesapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private static DataHelper INSTANCE;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavMovie";
    private static final String TABLE_FAVMOVIE = "t_movie";
    private static final String TABLE_FAVTV = "t_tv";
    private static final String KEY_ID = "id";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_DESC = "desk";
    private static final String KEY_PHOTO = "photo";





    public DataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public static DataHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new DataHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BUKU_TABLE = "CREATE TABLE " + TABLE_FAVMOVIE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAMA + " TEXT,"
                + KEY_DESC + " TEXT,"+KEY_PHOTO + " TEXT)";
        db.execSQL(CREATE_BUKU_TABLE);

        String CREATE_BUKU_TABLE2 = "CREATE TABLE " + TABLE_FAVTV + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAMA + " TEXT,"
                + KEY_DESC + " TEXT,"+KEY_PHOTO + " TEXT)";
        db.execSQL(CREATE_BUKU_TABLE2);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVMOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVTV);

        onCreate(db);
    }

    public boolean isAdded(String name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM t_movie WHERE nama = ?", new String[] {name});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean isAdded2(String name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM t_tv WHERE nama = ?", new String[] {name});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void save(Movie movie){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAMA, movie.getName());
        values.put(KEY_DESC, movie.getDescription());
        values.put(KEY_PHOTO,movie.getPhoto());

        db.insert(TABLE_FAVMOVIE, null, values);
        db.close();
    }


    public void save2(Movie movie){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAMA, movie.getName());
        values.put(KEY_DESC, movie.getDescription());
        values.put(KEY_PHOTO,movie.getPhoto());

        db.insert(TABLE_FAVTV, null, values);
        db.close();
    }

    public Movie findOne(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_FAVMOVIE, new String[]{KEY_ID,KEY_NAMA,KEY_DESC},
                KEY_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        Movie satu = new Movie();
        satu.setName(cursor.getString(0));
        satu.setDescription(cursor.getString(1));
        satu.setDescription(cursor.getString(2));


        return satu;
    }

    public ArrayList<Movie> findAll(){
        ArrayList<Movie> listMovie=new ArrayList<Movie>();
        String query="SELECT * FROM "+TABLE_FAVMOVIE;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Movie movie=new Movie();
                movie.setName(cursor.getString(1));
                movie.setDescription(cursor.getString(2));
                movie.setPhoto(cursor.getString(3));
                listMovie.add(movie);
            }while(cursor.moveToNext());
        }

        return listMovie;
    }

    public Cursor getCursorMovieData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVMOVIE, new String[] { KEY_NAMA,
                KEY_DESC, KEY_PHOTO },null, null, null, null, null);


        return cursor;
    }




    public ArrayList<Movie> getCursorData(){
        ArrayList<Movie> listMovie=new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVMOVIE, new String[] { KEY_NAMA,
                        KEY_DESC, KEY_PHOTO },null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Movie movie=new Movie();
                movie.setName(cursor.getString(0));
                movie.setDescription(cursor.getString(1));
                movie.setPhoto(cursor.getString(2));
                Log.d("_CONTENT_CURSOR",cursor.getString(0));
                listMovie.add(movie);
            }while(cursor.moveToNext());
        }
        return listMovie;
    }

//    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor){
//        ArrayList<Movie> movieItemList = new ArrayList<>();
//
//        while (movieCursor.moveToNext()){
//            String judul = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE));
//            String desc = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW));
//            String photo = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.PHOTO));
//            movieItemList.add(new Movie(judul,desc,photo));
//        }
//        return movieItemList;
//    }

    public static Movie mapCursorToObject(Cursor notesCursor) {
        notesCursor.moveToFirst();
        String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(KEY_NAMA));
        String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(KEY_DESC));
        String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(KEY_PHOTO));
        Movie movie=new Movie();
        movie.setName(title);
        movie.setDescription(description);
        movie.setPhoto(photo);
        return movie;
    }

    public Cursor queryAll() {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.query(TABLE_FAVMOVIE,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public void open() throws SQLException{
        database = this.getWritableDatabase();
    }



    public ArrayList<Movie> findAll2(){
        ArrayList<Movie> listMovie=new ArrayList<Movie>();
        String query="SELECT * FROM "+TABLE_FAVTV;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Movie movie=new Movie();
                movie.setName(cursor.getString(1));
                movie.setDescription(cursor.getString(2));
                movie.setPhoto(cursor.getString(3));
                listMovie.add(movie);
            }while(cursor.moveToNext());
        }

        return listMovie;
    }

//    public void update(Movie movie){
//        SQLiteDatabase db=this.getWritableDatabase();
//
//        ContentValues values=new ContentValues();
//        values.put(KEY_NAMA, movie.getName());
//        values.put(KEY_DESC, movie.getDescription());
//
//        db.update(TABLE_FAVMOVIE, values, KEY_ID+"=?", new String[]{String.valueOf(movie.getName())});
//        db.close();
//    }

    public void delete(Movie movie){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_FAVMOVIE, KEY_NAMA+"=?", new String[]{String.valueOf(movie.getName())});
        db.close();

    }

    public void delete2(Movie movie){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_FAVTV, KEY_NAMA+"=?", new String[]{String.valueOf(movie.getName())});
        db.close();

    }
}
