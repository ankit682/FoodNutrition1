package com.app.foodnutritionapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.foodnutritionapp.Activity.ProductDetail;
import com.app.foodnutritionapp.Item.ProductList;
import com.app.foodnutritionapp.R;
import com.app.foodnutritionapp.Util.Method;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Activity activity;
    private List<ProductList> roomLists;
    private Method method;
    private int columnWidth;

    public ProductAdapter(Activity activity, List<ProductList> roomLists) {
        this.activity = activity;
        this.roomLists = roomLists;
        method = new Method(activity);
        columnWidth = method.getScreenWidth();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.product_adapter, parent, false);

        return new ProductAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.imageView_room.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 2));
        holder.view.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth, columnWidth / 2));

        Picasso.get().load(roomLists.get(position).getRoom_image_thumb())
                .placeholder(R.mipmap.ic_launcher).into(holder.imageView_room, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                holder.progressBar.setVisibility(View.GONE);
            }


        });
        holder.textView_roomName.setText(roomLists.get(position).getRoom_name());
        holder.textView_totalRate.setText("(" + roomLists.get(position).getTotal_rate() + ")");

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetail.class);
                intent.putExtra("position", position);
                activity.startActivity(intent);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetail.class);
                intent.putExtra("position", position);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imageView_room;
        private TextView textView_roomName, textView_totalRate;
        private RelativeLayout relativeLayout;
        private View view;
        private Button button;
        private SmoothProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relativeLayout_room_adapter);
            button = itemView.findViewById(R.id.button_room_adapter);
            imageView_room = itemView.findViewById(R.id.imageView_room_adapter);
            view = itemView.findViewById(R.id.view_room_adapter);
            textView_roomName = itemView.findViewById(R.id.textView_roomName_room_adapter);
            textView_totalRate = itemView.findViewById(R.id.textView_totalRate_room_adapter);
            progressBar = itemView.findViewById(R.id.smoothProgressBar_room_adapter);

        }
    }
}
