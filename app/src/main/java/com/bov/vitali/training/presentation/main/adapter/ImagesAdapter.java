package com.bov.vitali.training.presentation.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bov.vitali.training.R;
import com.bov.vitali.training.data.model.Image;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import static com.bov.vitali.training.presentation.main.presenter.ImagesPresenter.IMAGES_COUNT;

public class ImagesAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {
    private static final int ADD_IMAGE_VIEW_TYPE = 1;
    private final ImagesClickListener clickListener;
    private Context context;
    private List<Image> images;

    public ImagesAdapter(List<Image> images, Context context, ImagesClickListener listener) {
        this.images = images;
        this.context = context;
        this.clickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ADD_IMAGE_VIEW_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_image, parent, false);
            return new AddImageViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof ImageViewHolder) {
                ImageViewHolder vh = (ImageViewHolder) holder;
                Image image = images.get(position);
                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(image.getBitmap())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                vh.imageView.setImageBitmap(resource);
                            }
                        });
                vh.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
            } else if (holder instanceof AddImageViewHolder) {
                AddImageViewHolder vh = (AddImageViewHolder) holder;
                Glide.with(vh.itemView.getContext())
                        .asBitmap()
                        .load(context.getResources().getDrawable(R.drawable.ic_add_image))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                vh.imageView.setImageBitmap(resource);
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (images.size() == 0) {
            return 1;
        }
        if (images.size() <= IMAGES_COUNT) {
            return images.size() + 1;
        } else {
            return images.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == images.size()) {
            return ADD_IMAGE_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    public void setImages(@NonNull List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View selectedOverlay;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            itemView.setOnClickListener(v -> clickListener.onImageClick(images.get(getAdapterPosition()).getUri(), getAdapterPosition()));
            itemView.setOnLongClickListener(view -> {
                clickListener.onLongImageClick(getAdapterPosition());
                return true;
            });
        }
    }

    class AddImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        AddImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivAddImage);
            itemView.setOnClickListener(v -> clickListener.onAddImageClick());
        }
    }

    public interface ImagesClickListener {
        void onImageClick(Uri uri, int position);

        void onLongImageClick(int position);

        void onAddImageClick();
    }
}