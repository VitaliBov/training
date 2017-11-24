package com.bov.vitali.training.presentation.main.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bov.vitali.training.R;

import java.util.List;

import static com.bov.vitali.training.presentation.main.presenter.ImagesPresenter.IMAGES_COUNT;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ADD_IMAGE_VIEW_TYPE = 1;
    private final ImagesClickListener clickListener;
    private final ImagesLongClickListener longClickListener;
    private Context context;
    private List<Bitmap> images;

    public ImagesAdapter(List<Bitmap> images, Context context, ImagesClickListener listener, ImagesLongClickListener longListener) {
        this.images = images;
        this.context = context;
        this.clickListener = listener;
        this.longClickListener = longListener;
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
                vh.imageView.setImageBitmap(images.get(position));
            } else if (holder instanceof AddImageViewHolder) {
                AddImageViewHolder vh = (AddImageViewHolder) holder;
                vh.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_image));
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

    public void setBitmaps(@NonNull List<Bitmap> bitmaps) {
        this.images = bitmaps;
        notifyDataSetChanged();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(v -> clickListener.onImageClick(images.get(getAdapterPosition())));
            itemView.setOnLongClickListener(view -> {
                longClickListener.onLongImageClick(images.get(getAdapterPosition()));
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
        void onImageClick(Bitmap bitmap);

        void onAddImageClick();
    }

    public interface ImagesLongClickListener {
        void onLongImageClick(Bitmap bitmap);
    }
}