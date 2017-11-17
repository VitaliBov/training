package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.bov.vitali.training.data.database.entity.User;

import java.util.List;
import java.util.Objects;

class DiffDatabaseListCallback extends DiffUtil.Callback {
    @NonNull private List<User> oldUsers;
    @NonNull private List<User> newUsers;

    DiffDatabaseListCallback(@NonNull List<User> oldUsers,
                             @NonNull List<User> newUsers) {
        this.oldUsers = oldUsers;
        this.newUsers = newUsers;
    }

    @Override
    public int getOldListSize() {
        return oldUsers.size();
    }

    @Override
    public int getNewListSize() {
        return newUsers.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldUsers.get(oldItemPosition).getId(), newUsers.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUsers.get(oldItemPosition).equals(newUsers.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}