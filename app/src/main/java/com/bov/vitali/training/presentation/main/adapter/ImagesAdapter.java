package com.bov.vitali.training.presentation.main.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bov.vitali.training.R;
import com.bov.vitali.training.data.model.Image;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MAX_IMAGES_COUNT = 10;
    private static final int IMAGE_VIEW_TYPE = 1;
    private static final int ADD_IMAGE_VIEW_TYPE = 2;
    private final ImagesClickListener clickListener;
    private Context context;
    private List<Image> images;
    private List<Image> selectedImages = new ArrayList<>();

    public ImagesAdapter(Context context, @NonNull List<Image> images, ImagesClickListener listener) {
        this.context = context;
        this.images = images;
        this.clickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case IMAGE_VIEW_TYPE:
                View image_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
                return new ImageViewHolder(image_view);
            case ADD_IMAGE_VIEW_TYPE:
                View add_image_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_image, parent, false);
                return new AddImageViewHolder(add_image_view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder vh = (ImageViewHolder) holder;

            Image image = images.get(position);
            final boolean isSelected = getSelectedItems().contains(image);
            vh.bind(image, isSelected);
        } else if (holder instanceof AddImageViewHolder) {
            AddImageViewHolder vh = (AddImageViewHolder) holder;
            vh.bind(context.getResources().getDrawable(R.drawable.ic_add_image));
        }
    }

    @Override
    public int getItemCount() {
        if (images.size() < MAX_IMAGES_COUNT) {
            return images.size() + 1;
        } else {
            return images.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == images.size() && position < MAX_IMAGES_COUNT) {
            return ADD_IMAGE_VIEW_TYPE;
        }
        return IMAGE_VIEW_TYPE;
    }

    public void setImages(@NonNull List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public void toggleSelection(Image image) {
        if (isSelected(image)) {
            selectedImages.remove(image);
        } else {
            selectedImages.add(image);
        }
        notifyDataSetChanged();
    }

    private boolean isSelected(Image image) {
        return selectedImages.contains(image);
    }

    public List<Image> getSelectedItems() {
        return selectedImages;
    }

    public int getSelectedItemCount() {
        return selectedImages.size();
    }

    public void clearSelection() {
        selectedImages.clear();
        notifyDataSetChanged();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View selectedOverlay;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            selectedOverlay = itemView.findViewById(R.id.selected_overlay);
            itemView.setOnClickListener(v -> clickListener.onImageClick(images.get(getAdapterPosition())));
            itemView.setOnLongClickListener(view -> {
                clickListener.onLongImageClick(images.get(getAdapterPosition()));
                return true;
            });
        }

        void bind(Image image, boolean isSelected) {
            if (image.getChangedUri() != null) {
                Glide.with(context)
                        .load(image.getChangedUri())
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(image.getOriginalUri())
                        .into(imageView);
            }
            updateOverlay(isSelected);
        }

        void updateOverlay(boolean isSelected) {
            selectedOverlay.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }
    }

    class AddImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        AddImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivAddImage);
            itemView.setOnClickListener(v -> clickListener.onAddImageClick());
        }

        void bind(Drawable drawable) {
            Glide.with(context)
                    .load(drawable)
                    .into(imageView);
        }
    }

    public interface ImagesClickListener {
        void onImageClick(Image image);

        void onLongImageClick(Image image);

        void onAddImageClick();
    }
}