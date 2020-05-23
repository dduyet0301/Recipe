package com.bbbbnnnn.readdatabase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbbbnnnn.readdatabase.R;
import com.bbbbnnnn.readdatabase.model.Recipe;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyDatabaseAdapter extends RecyclerView.Adapter<MyDatabaseAdapter.MyDatabaseHolder> {

    private ArrayList<Recipe> data;
    private LayoutInflater inflater;
    private RecipeItemListener listener;

    public MyDatabaseAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setData(ArrayList<Recipe> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(RecipeItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyDatabaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_recipe, parent, false);
        return new MyDatabaseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDatabaseHolder holder, final int position) {
        final Recipe item = data.get(position);
        holder.bindData(item);
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRecipeItemClick(position, item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MyDatabaseHolder extends RecyclerView.ViewHolder {

        private ImageView imgThumbnail;
        private TextView tvTenHinh;

        public MyDatabaseHolder(@NonNull View itemView) {
            super(itemView);
            tvTenHinh = itemView.findViewById(R.id.textViewTenHinh);
            imgThumbnail = itemView.findViewById(R.id.imageViewThumbnail);
        }

        public void bindData(Recipe item) {

            imgThumbnail.setClipToOutline(true);
            tvTenHinh.setText(item.getTenHinh());
            loadImage(imgThumbnail, item.getLinkHinh());
        }
    }

    public interface RecipeItemListener {
        void onRecipeItemClick(int position, Recipe item);
    }

    private void loadImage(ImageView im, String link) {
        Glide.with(im)
                .load(link)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_launcher)
                .into(im);
    }

    public void setFilter(ArrayList<Recipe> newList) {
        data = new ArrayList<>();
        data.addAll(newList);
        notifyDataSetChanged();
    }
}
