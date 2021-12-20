package com.example.rahul;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_IssuedBookAdapter extends FirebaseRecyclerAdapter<ListData,Admin_IssuedBookAdapter.viewholder> {
    public Admin_IssuedBookAdapter(@NonNull FirebaseRecyclerOptions<ListData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Admin_IssuedBookAdapter.viewholder holder, int position, @NonNull ListData listData) {
        holder.urusername.setText(listData.getUsername());
        holder.urBooktitle.setText(listData.getTitle());
        holder.urphoneno.setText(listData.getPhoneno());
        holder.urReturndate.setText(listData.getReturndate());
        holder.urcurrentdate.setText(listData.getCurrentdate());
    }


    @NonNull
    @Override
    public Admin_IssuedBookAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userlistview,parent,false);
        return new viewholder(view);
    }
    class viewholder extends RecyclerView.ViewHolder{

        TextView urusername,urBooktitle,urphoneno,urReturndate,urcurrentdate;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            urusername=itemView.findViewById(R.id.urUsername);
            urBooktitle=itemView.findViewById(R.id.urBookTitle);
            urphoneno=itemView.findViewById(R.id.urPhonrno);
            urReturndate=itemView.findViewById(R.id.urreturndate);
            urcurrentdate=itemView.findViewById(R.id.urcurrentdate);

        }
    }
}
