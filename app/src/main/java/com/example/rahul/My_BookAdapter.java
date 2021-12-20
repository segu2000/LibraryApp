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

public class My_BookAdapter extends FirebaseRecyclerAdapter<ListData,My_BookAdapter.viewholder> {
    public My_BookAdapter(@NonNull FirebaseRecyclerOptions<ListData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull My_BookAdapter.viewholder holder, int position, @NonNull ListData listData) {
        holder.urusername.setText(listData.getUsername());
        holder.urBooktitle.setText(listData.getTitle());
        holder.urphoneno.setText(listData.getPhoneno());
        holder.urReturndate.setText(listData.getReturndate());
        holder.urcurrentdate.setText(listData.getCurrentdate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.urusername.getContext());
                builder.setTitle("RETURN BOOK");
                builder.setMessage("Do you want Return this book..");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("LibBooks")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }


    @NonNull
    @Override
    public My_BookAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
