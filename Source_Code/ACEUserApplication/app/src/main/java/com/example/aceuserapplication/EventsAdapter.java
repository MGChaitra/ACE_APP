package com.example.aceuserapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewAdapter> {

    private Context context;
    private ArrayList<EventsData> list;




    // organising
    private OnOrgClickListener onOrgClickListener;
    public interface OnOrgClickListener{
        void onClickO(int position);
    }

    public void setOnOrgClickListener(OnOrgClickListener onOrgClickListener) {
        this.onOrgClickListener = onOrgClickListener;
    }



    public EventsAdapter(Context context, ArrayList<EventsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventsAdapter.EventsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.organise_events_activity,parent,false);
        return new EventsAdapter.EventsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventsViewAdapter holder, int position) {

        EventsData currentItem=list.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.button.setText(currentItem.getType());

        try {
            if (currentItem.getImage()!=null)
                Glide.with(context).load(currentItem.getImage()).into(holder.image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {


        return list.size();
    }

    public class EventsViewAdapter extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;
        private Button button;


        public EventsViewAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleOrg);
            image = itemView.findViewById(R.id.imageOrg);
            button = itemView.findViewById(R.id.button2);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOrgClickListener.onClickO(getAdapterPosition());
                }
            });


        }
    }
}

