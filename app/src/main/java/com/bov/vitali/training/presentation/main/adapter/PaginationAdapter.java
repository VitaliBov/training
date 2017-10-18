package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bov.vitali.training.R;
import com.bov.vitali.training.data.model.Film;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<PaginationAdapter.ViewHolder> {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";
    private List<Film> films;

    public PaginationAdapter() {
        films = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagination, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Film film = films.get(position);
        Picasso.with(holder.itemView.getContext()).cancelRequest(holder.ivPagination);
        holder.tvPaginationName.setText(film.getTitle());
        holder.tvPaginationDescription.setText(film.getOverview());
        try {
            Picasso.with(holder.itemView.getContext())
                    .load(BASE_URL_IMG + film.getPosterPath())
                    .into(holder.ivPagination);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return films == null ? 0 : films.size();
    }

    public void addAll(@NonNull List<Film> films) {
        List<Film> newFilms = this.films;
        newFilms.addAll(films);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffPaginationCallback(this.films, newFilms));
        this.films = newFilms;
        result.dispatchUpdatesTo(this);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvPaginationName, tvPaginationDescription;
        ImageView ivPagination;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_pagination);
            tvPaginationName = itemView.findViewById(R.id.tvPaginationName);
            tvPaginationDescription = itemView.findViewById(R.id.tvPaginationDescription);
            ivPagination = itemView.findViewById(R.id.ivPagination);
        }
    }
}