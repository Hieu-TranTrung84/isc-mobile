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
import edu.hieutt.majorshop.model.Menu;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderViewHolder> {
    private List<Menu> list;

    public PlaceOrderAdapter(List<Menu> list) {
        this.list = list;
    }

    public void updateData(List<Menu> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaceOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order_recycler_row, parent, false);

        return new PlaceOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOrderViewHolder holder, int position) {
        holder.tv_name_place_order.setText(list.get(position).getName());
        holder.tv_price_place_order.setText("Price: $" + String.format("%.2f",
                list.get(position).getPrice() * list.get(position).getTotalInCart()));
        holder.tv_quantity_place_order.setText("Quantity: " + list.get(position).getTotalInCart());

        Glide.with(holder.img_thumb_place_order)
                .load(list.get(position).getUrl())
                .into(holder.img_thumb_place_order);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class PlaceOrderViewHolder extends RecyclerView.ViewHolder {
    protected ImageView img_thumb_place_order;
    protected TextView tv_name_place_order, tv_price_place_order, tv_quantity_place_order;

    public PlaceOrderViewHolder(@NonNull View itemView) {
        super(itemView);
        img_thumb_place_order = itemView.findViewById(R.id.img_thumb_place_order);
        tv_name_place_order = itemView.findViewById(R.id.tv_name_place_order);
        tv_price_place_order = itemView.findViewById(R.id.tv_price_place_order);
        tv_quantity_place_order = itemView.findViewById(R.id.tv_quantity_place_order);
    }
}