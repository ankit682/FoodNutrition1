package com.app.foodnutritionapp.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.foodnutritionapp.Item.GalleryDetailList;
import com.app.foodnutritionapp.R;
import com.app.foodnutritionapp.Util.Method;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private Method method;
    List<GalleryDetailList> galleryDetailLists;

    public GalleryRecyclerAdapter(Activity activity, List<GalleryDetailList> galleryDetailLists) {
        this.activity = activity;
        this.galleryDetailLists = galleryDetailLists;
        method = new Method(activity);
    }

    @Override
    public GalleryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.gallery_recyclerview_adapter, parent, false);

        return new GalleryRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryRecyclerAdapter.ViewHolder holder, final int position) {

        Picasso.get().load(galleryDetailLists.get(position).getWallpaper_image_thumb())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return galleryDetailLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_gr_adapter);

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

}