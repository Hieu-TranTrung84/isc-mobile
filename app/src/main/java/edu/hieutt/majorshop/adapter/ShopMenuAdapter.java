package edu.hieutt.majorshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.hieutt.majorshop.R;
import edu.hieutt.majorshop.model.Menu;

public class ShopMenuAdapter extends RecyclerView.Adapter<ShopMenuViewHolder> {

    private List<Menu> list;
    private IClickListener listener;

    public ShopMenuAdapter(List<Menu> list, IClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void updateData(List<Menu> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface IClickListener {
        public void onAddToCartClick(Menu menu);

        public void onUpdateCartClick(Menu menu);

        public void onRemoveFromCartClick(Menu menu);
    }

    @NonNull
    @Override
    public ShopMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return new ShopMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopMenuViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_price.setText("Price: $" + list.get(position).getPrice());
        holder.tv_size.setText("Size: " + list.get(position).getSize());
        holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = list.get(position);
                menu.setTotalInCart(1);
                listener.onAddToCartClick(menu);
                holder.layout_add_more.setVisibility(View.VISIBLE);
                holder.tv_add_to_cart.setVisibility(View.GONE);
                holder.tv_count.setText(menu.getTotalInCart() + "");
            }
        });
        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = list.get(position);
                int total = menu.getTotalInCart();
                total--;
                if (total > 0) {
                    menu.setTotalInCart(total);
                    listener.onUpdateCartClick(menu);
                    holder.tv_count.setText(total + "");
                } else {
                    holder.layout_add_more.setVisibility(View.GONE);
                    holder.tv_add_to_cart.setVisibility(View.VISIBLE);
                    menu.setTotalInCart(total);
                    listener.onRemoveFromCartClick(menu);
                }
            }
        });
        holder.img_add_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = list.get(position);
                int total = menu.getTotalInCart();
                total++;
                if (total <= 10) {
                    menu.setTotalInCart(total);
                    listener.onUpdateCartClick(menu);
                    holder.tv_count.setText(total + "");
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToCartClick(list.get(position));
            }
        });
        Glide.with(holder.mn_img_thumb)
                .load(list.get(position).getUrl())
                .into(holder.mn_img_thumb);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ShopMenuViewHolder extends RecyclerView.ViewHolder {
    protected TextView tv_name, tv_price, tv_size, tv_add_to_cart, tv_count;
    protected ImageView mn_img_thumb, img_minus, img_add_one;
    protected LinearLayout layout_add_more;

    public ShopMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_price = itemView.findViewById(R.id.tv_price);
        tv_size = itemView.findViewById(R.id.tv_size);
        tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);
        tv_count = itemView.findViewById(R.id.tv_count);
        mn_img_thumb = itemView.findViewById(R.id.mn_img_thumb);
        img_minus = itemView.findViewById(R.id.img_minus);
        img_add_one = itemView.findViewById(R.id.img_add_one);
        layout_add_more = itemView.findViewById(R.id.layout_add_more);

    }
}