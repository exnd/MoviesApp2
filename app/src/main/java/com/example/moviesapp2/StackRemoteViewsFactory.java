package com.example.moviesapp2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private ArrayList<Movie> listMovie= new ArrayList<>();
    private final Context mContext;


    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }




    @Override
    public void onDataSetChanged() {

        DataHelper databaseHandler=new DataHelper(this.mContext);
        listMovie=databaseHandler.findAll();


        Bitmap bitmapFactory;
        try{

            for (int i = 0; i < listMovie.size() ; i++) {
                bitmapFactory = Glide.with(mContext).asBitmap()
                        .load(listMovie.get(i).getPhoto())
                        .submit()
                        .get();

               mWidgetItems.add(bitmapFactory);

            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }


//        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));



        Bundle extras = new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
