package com.bov.vitali.training.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bov.vitali.training.R;
import com.bov.vitali.training.data.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseListAdapter extends RecyclerView.Adapter<DatabaseListAdapter.ViewHolder> {
    @NonNull private List<User> users;

    public DatabaseListAdapter() {
        this.users = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_database, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvUsername.setText(user.getUsername());
        holder.tvCity.setText(user.getAddress().getCity());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(@NonNull List<User> users) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffDatabaseListCallback(this.users, users));
        this.users = users;
        result.dispatchUpdatesTo(this);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvCity;

        ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvDatabaseUsername);
            tvCity = itemView.findViewById(R.id.tvDatabaseCity);
        }
    }
}