package com.example.myadminapp;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG = "addBill";
    public static final String CLASS_UPDATE_DIALOG = "updateBill";
    public static final String STUDENT_ADD_DIALOG = "addExpense";
    public static final String STUDENT_UPDATE_DIALOG = "updateExpense";

    OnClickListener listener;
    private int id;
    private String name;

    public MyDialog(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public MyDialog() {

    }

    public interface OnClickListener{
        void onClick(String text1 , String text2);
    }

    public void setListener(OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if(getTag().equals(CLASS_ADD_DIALOG)) dialog = getAddClassDialog();
        if(getTag().equals(STUDENT_ADD_DIALOG)) dialog = getAddStudentDialog();
        if(getTag().equals(CLASS_UPDATE_DIALOG)) dialog = getUpdatedClassDialog();
        if(getTag().equals(STUDENT_UPDATE_DIALOG)) dialog = getUpdatedStudentDialog();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdatedStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Uodate Expense");

        EditText roll_edit = view.findViewById(R.id.edt01);
        EditText name_edit = view.findViewById(R.id.edt02);

        roll_edit.setHint("Name");
        name_edit.setHint("Price");

        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Updated");

        roll_edit.setText(id +"");
        name_edit.setText(name+"");
        roll_edit.setEnabled(false);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String roll = roll_edit.getText().toString();
            String name = name_edit.getText().toString();
            listener.onClick(roll , name);

        });

        return builder.create();

    }

    private Dialog getUpdatedClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Update Bill");

        EditText class_edit = view.findViewById(R.id.edt01);

        EditText subject_edit = view.findViewById(R.id.edt02);

        class_edit.setHint("Srn");
        subject_edit.setHint("Name");

        class_edit.setVisibility(View.GONE);
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Updated");

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = class_edit.getText().toString()+"";
            String subName = subject_edit.getText().toString();
            listener.onClick(className , subName);
            dismiss();
        });

        return builder.create();
    }

    private Dialog getAddStudentDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add new Expense");

        EditText roll_edit = view.findViewById(R.id.edt01);
        EditText name_edit = view.findViewById(R.id.edt02);

        roll_edit.setHint("Name");
        name_edit.setHint("Price");

        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String roll = roll_edit.getText().toString();
            String name = name_edit.getText().toString();
            roll_edit.setText("");
            name_edit.setText("");
            listener.onClick(roll , name);

        });

        return builder.create();

    }

    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add new Bill");

        EditText class_edit = view.findViewById(R.id.edt01);

        EditText subject_edit = view.findViewById(R.id.edt02);

        class_edit.setHint("Class Name");
        class_edit.setVisibility(View.GONE);
        subject_edit.setHint("Name");

        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String className = class_edit.getText().toString()+"";
            String subName = subject_edit.getText().toString();
            listener.onClick(className , subName);
            dismiss();
        });

        return builder.create();
    }
}

