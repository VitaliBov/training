package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.bov.vitali.training.data.model.Publication;

import java.util.List;

class DiffCallback extends DiffUtil.Callback {

    private List<Publication> oldPublications;
    private List<Publication> newPublications;

    DiffCallback(List<Publication> oldPublications, List<Publication> newPublications) {
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
        return oldPublications.get(oldItemPosition).getId() == newPublications.get(newItemPosition).getId();
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