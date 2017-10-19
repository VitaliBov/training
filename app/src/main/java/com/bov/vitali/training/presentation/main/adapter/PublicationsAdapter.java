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
import com.bov.vitali.training.data.model.Publication;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PublicationsAdapter extends RecyclerView.Adapter<PublicationsAdapter.ViewHolder> {
    @NonNull
    private List<Publication> publications;

    public PublicationsAdapter() {
        this.publications = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_publication, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Publication publication = publications.get(position);
        Picasso.with(holder.itemView.getContext()).cancelRequest(holder.ivPublication);
        holder.tvName.setText(publication.getName());
        holder.tvUrl.setText(publication.getUrl());
        holder.tvDescription.setText(publication.getDescription());
        if (!publication.getImageUrl().isEmpty()) {
            Picasso.with(holder.itemView.getContext())
                    .load(publication.getImageUrl())
                    .into(holder.ivPublication);
        }
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    public void setPublications(@NonNull List<Publication> publications) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffPublicationsCallback(this.publications, publications));
        this.publications = publications;
        result.dispatchUpdatesTo(this);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName, tvUrl, tvDescription;
        ImageView ivPublication;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_publications);
            tvName = itemView.findViewById(R.id.tvPublicationName);
            tvUrl = itemView.findViewById(R.id.tvPublicationUrl);
            tvDescription = itemView.findViewById(R.id.tvPublicationDescription);
            ivPublication = itemView.findViewById(R.id.ivPublication);
        }
    }
}