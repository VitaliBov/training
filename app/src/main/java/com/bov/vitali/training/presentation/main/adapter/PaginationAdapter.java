package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.NonNull;
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

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Film> films;
    private boolean isLoadingAdded = false;

    public PaginationAdapter() {
        films = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View view = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.item_pagination, parent, false);
        viewHolder = new FilmViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Film film = films.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                FilmViewHolder viewHolder = (FilmViewHolder) holder;
                Picasso.with(holder.itemView.getContext()).cancelRequest(((FilmViewHolder) holder).ivPagination);
                viewHolder.tvPaginationName.setText(film.getTitle());
                viewHolder.tvPaginationDescription.setText(film.getOverview());
                if (!film.getPosterPath().isEmpty()) {
                    Picasso.with(holder.itemView.getContext())
                            .load(BASE_URL_IMG + film.getPosterPath())
                            .into(((FilmViewHolder) holder).ivPagination);
                }
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return films == null ? 0 : films.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == films.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Film film) {
        films.add(film);
        notifyItemInserted(films.size() - 1);
    }

    public void addAll(List<Film> films) {
        for (Film film : films) {
            add(film);
        }
    }

    private void remove(Film film) {
        int position = films.indexOf(false);
        if (position > -1) {
            films.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Film());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = films.size() - 1;
        Film item = getItem(position);
        if (item != null) {
            films.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Film getItem(int position) {
        return films.get(position);
    }

    private class FilmViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvPaginationName, tvPaginationDescription;
        ImageView ivPagination;

        FilmViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_pagination);
            tvPaginationName = itemView.findViewById(R.id.tvPaginationName);
            tvPaginationDescription = itemView.findViewById(R.id.tvPaginationDescription);
            ivPagination = itemView.findViewById(R.id.ivPagination);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }
}