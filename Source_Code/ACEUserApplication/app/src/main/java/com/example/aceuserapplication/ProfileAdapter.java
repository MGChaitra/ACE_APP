package com.example.aceuserapplication;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfiltViewAdapter> {



    private Context context;
    private List<String> titles;

    public ProfileAdapter(Context context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }


    @NonNull
    @Override
    public ProfileAdapter.ProfiltViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.class_items,parent,false);
        return new ProfiltViewAdapter(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ProfiltViewAdapter holder, int position) {
        holder.title.setText(titles.get(position));
    }



    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ProfiltViewAdapter extends RecyclerView.ViewHolder  implements View.OnCreateContextMenuListener {

        TextView title;
        public ProfiltViewAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.class_tv);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"DELETE");
        }
    }
}
