package edu.hieutt.majorshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.hieutt.majorshop.R;
import edu.hieutt.majorshop.model.Shop;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private List<Shop> list;
    private IClickListener listener;

    public ShopAdapter(List<Shop> list, IClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void updateDate(List<Shop> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface IClickListener {
        void onItemClick(Shop shop);
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.tv_shop_name.setText(list.get(position).getName());
        holder.tv_shop_address.setText("Address: " + list.get(position).getAddress());
        holder.tv_shop_address.setVisibility(View.GONE);
        holder.tv_shop_hours.setText("Today's hours: " + list.get(position).getHours().getTodaysHours());
        holder.tv_shop_hours.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(list.get(position));
            }
        });
        Glide.with(holder.img_thumb)
                .load(list.get(position).getImage())
                .into(holder.img_thumb);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ShopViewHolder extends RecyclerView.ViewHolder {
    protected TextView tv_shop_name, tv_shop_address, tv_shop_hours;
    protected ImageView img_thumb;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_shop_name = itemView.findViewById(R.id.tv_shop_name);
        tv_shop_address = itemView.findViewById(R.id.tv_shop_address);
        tv_shop_hours = itemView.findViewById(R.id.tv_shop_hours);
        img_thumb = itemView.findViewById(R.id.img_thumb);
    }
}