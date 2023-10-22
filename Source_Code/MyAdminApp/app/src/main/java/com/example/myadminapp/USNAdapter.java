package com.example.myadminapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class USNAdapter extends RecyclerView.Adapter<USNAdapter.USNViewAdapter> {



    private Context context;
    private List<String> titles;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public USNAdapter(Context context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }


    @NonNull
    @Override
    public USNAdapter.USNViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.usn_store,parent,false);
        return new USNViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull USNAdapter.USNViewAdapter holder, int position) {
        holder.title.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class USNViewAdapter extends RecyclerView.ViewHolder{

        TextView title;
        public USNViewAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.class_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }
}
