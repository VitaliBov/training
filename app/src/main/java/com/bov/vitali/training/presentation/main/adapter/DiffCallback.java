package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.bov.vitali.training.data.model.Publication;

import java.util.List;
import java.util.Objects;

class DiffCallback extends DiffUtil.Callback {

    @NonNull
    private List<Publication> oldPublications;
    @NonNull
    private List<Publication> newPublications;

    DiffCallback(@NonNull List<Publication> oldPublications, @NonNull List<Publication> newPublications) {
        this.oldPublications = oldPublications;
        this.newPublications = newPublications;
    }

    @Override
    public int getOldListSize() {
        return oldPublications.size();
    }

    @Override
    public int getNewListSize() {
        return newPublications.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldPublications.get(oldItemPosition).getId(), newPublications.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPublications.get(oldItemPosition).equals(newPublications.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}