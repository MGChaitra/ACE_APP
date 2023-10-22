package com.example.myadminapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    ArrayList<CartItem> cartItems;
    Context context;

    //remove
    private OnRemoveClickListener onRemoveClickListener;
    public interface OnRemoveClickListener{
        void onClickR(int position);
    }

    public void setOnRemoveClickListener(OnRemoveClickListener onItemClickListener) {
        this.onRemoveClickListener = onItemClickListener;
    }

    //back

    private OnBackClickListener onBackClickListener;
    public interface OnBackClickListener{
        void onClickB(int position);
    }

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    //forward

    private OnForwardClickListener onForwardClickListener;
    public interface OnForwardClickListener{
        void onClickF(int position);
    }

    public void setOnForwardClickListener(OnForwardClickListener onForwardClickListener) {
        this.onForwardClickListener = onForwardClickListener;
    }


    private OnItemClickListner onItemClickListner;
    public interface OnItemClickListner{
        void onCLick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }



    public CartAdapter(Context context , ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.context = context;
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView priceing;
        TextView remove;
        CardView cardView;
        ImageView c;
        public CartViewHolder(@NonNull View itemView ) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName_tv);
            priceing = itemView.findViewById(R.id.price_tv);
            remove = itemView.findViewById(R.id.remove);
            cardView = itemView.findViewById(R.id.cardView);
            c = itemView.findViewById(R.id.c);

            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListner.onCLick(getAdapterPosition());
                }
            });


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRemoveClickListener.onClickR(getAdapterPosition());
                }
            });
        }
    }




    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item , parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        holder.itemName.setText(cartItems.get(position).getItemName());
        holder.priceing.setText(cartItems.get(position).getPrice());
        holder.cardView.setCardBackgroundColor(getBackground(position));
    }

    private int getBackground(int position){
        String status= cartItems.get(position).getStatus();
        if(status.equals("P"))
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
        else if (status.equals("A")) {
            return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));
        }

        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.white)));

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}
