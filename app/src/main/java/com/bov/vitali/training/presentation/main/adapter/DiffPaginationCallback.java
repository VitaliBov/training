package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.bov.vitali.training.data.model.Film;

import java.util.List;
import java.util.Objects;

class DiffPaginationCallback extends DiffUtil.Callback {

    @NonNull
    private List<Film> oldFilms;
    @NonNull
    private List<Film> newFilms;

    DiffPaginationCallback(@NonNull List<Film> oldFilms, @NonNull List<Film> newFilms) {
        this.oldFilms = oldFilms;
        this.newFilms = newFilms;
    }

    @Override
    public int getOldListSize() {
        return oldFilms.size();
    }

    @Override
    public int getNewListSize() {
        return newFilms.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldFilms.get(oldItemPosition).getId(), newFilms.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFilms.get(oldItemPosition).equals(newFilms.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}