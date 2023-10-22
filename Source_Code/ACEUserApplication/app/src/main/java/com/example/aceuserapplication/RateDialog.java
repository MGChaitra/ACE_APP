package com.example.aceuserapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class RateDialog extends DialogFragment {

    public RateDialog(){

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        dialog = getUpdatedStars();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdatedStars(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.rate_dialog, null);


        builder.setView(view);

        ImageView star1 = view.findViewById(R.id.star_one);
        ImageView star2 = view.findViewById(R.id.star_tow);
        ImageView star3 = view.findViewById(R.id.star_three);
        ImageView star4 = view.findViewById(R.id.star_four);
        ImageView star5 = view.findViewById(R.id.star_five);
        TextView save = view.findViewById(R.id.saveRate);

        save.setEnabled(false);

        star2.setEnabled(false);
        star3.setEnabled(false);
        star4.setEnabled(false);
        star5.setEnabled(false);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star2.setEnabled(true);
                star1.setImageDrawable(getResources().getDrawable(R.drawable.baseline_star_24));
                save.setEnabled(true);
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star3.setEnabled(true);
                star2.setImageDrawable(getResources().getDrawable(R.drawable.baseline_star_24));

            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star4.setEnabled(true);
                star3.setImageDrawable(getResources().getDrawable(R.drawable.baseline_star_24));
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star5.setEnabled(true);
                star4.setImageDrawable(getResources().getDrawable(R.drawable.baseline_star_24));
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star5.setImageDrawable(getResources().getDrawable(R.drawable.baseline_star_24));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Thanks for the rating", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


        return builder.create();
    }
}
