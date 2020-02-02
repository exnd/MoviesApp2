package com.example.moviesapp2.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.moviesapp2.DataHelper;
import com.example.moviesapp2.ListMovieAdapter;

public class MovieProvider extends ContentProvider {

    private static final int GETALLDATA = 1;

    private static final String AUTHORITY = "com.example.moviesapp2";
    private static final String PATH_GETDATA = "GETDATA";
    private DataHelper DataHelper;
    private ListMovieAdapter listMovieAdapter;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.dicoding.picodiploma.mynotesapp/GETDATA
        MATCHER.addURI(AUTHORITY, PATH_GETDATA , GETALLDATA);
    }


    public MovieProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {

        DataHelper = com.example.moviesapp2.DataHelper.getInstance(getContext());
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        Cursor cursor = null;
        switch (MATCHER.match(uri)) {
            case GETALLDATA:
                cursor = DataHelper.getCursorMovieData();
                break;
            default:
                cursor = null;
                break;
        }


        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
