package com.example.moviesapp2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {


    private ArrayList<Movie> listMovie;
    private RecyclerViewInterface recyclerViewInterface;
    private static ListMovieAdapter listMovieAdapter;



    public ListMovieAdapter(ArrayList<Movie> listMovie , RecyclerViewInterface recyclerViewInterface) {
        this.listMovie = listMovie;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movies, viewGroup, false);
        return new ListViewHolder(view);

    }



    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {



        Movie movie = listMovie.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getPhoto())
                .into(holder.imgPhoto);
        holder.tvName.setText(movie.getName());
        holder.tvDescription.setText(movie.getDescription());



        //btn favorite click
        final MainActivity mainActivity = new MainActivity();

        holder.fav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                recyclerViewInterface.onItemClick(position);

            }
        });


        holder.hapus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                recyclerViewInterface.onHapusClick(position);

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent detailIntent = new Intent(holder.itemView.getContext(), DetailMovie.class);
                detailIntent.putExtra(DetailMovie.EXTRA_MOVIE, listMovie.get(holder.getAdapterPosition()));
                holder.itemView.getContext().startActivity(detailIntent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvName, tvDescription;
        Button fav,hapus;
        ListViewHolder(View itemView) {
            super(itemView);
                imgPhoto = itemView.findViewById(R.id.img_movie_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            fav = itemView.findViewById(R.id.btn_set_favorite);
            hapus = itemView.findViewById(R.id.btn_set_share);
        }
    }
}
