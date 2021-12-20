package com.example.rahul;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class UserAdapter extends FirebaseRecyclerAdapter<ListData,UserAdapter.viewholder> {

    public UserAdapter(@NonNull FirebaseRecyclerOptions<ListData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull ListData listData) {
        holder.urId.setText(listData.getId());
        holder.urTitle.setText(listData.getTitle());
        holder.urAuthor.setText(listData.getAuthor());
        holder.urUnits.setText(listData.getUnits());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.urTitle.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_userdetails))
                        .setExpanded(true,1100)
                        .create();
                View myview=dialogPlus.getHolderView();
                TextView bookname=myview.findViewById(R.id.Bookname);
                bookname.setText(listData.getTitle());
               TextView yourname=myview.findViewById(R.id.Yourname);
               TextView phoneno=myview.findViewById(R.id.phoneno);
                Button submit=myview.findViewById(R.id.Ordernow);

                yourname.setText(listData.getUsername());
                phoneno.setText(listData.getPhoneno());


                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("username",yourname.getText().toString());
                        map.put("phoneno",phoneno.getText().toString());
                        map.put("Title",bookname.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("LibBooks")
                                .push().setValue(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        yourname.setText("");
                                        phoneno.setText("");
                                        bookname.setText("");
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userrow,parent,false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView urTitle,urAuthor,urUnits,urId;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            urId=itemView.findViewById(R.id.urid);
            urAuthor=itemView.findViewById(R.id.urAuthor);
            urTitle=itemView.findViewById(R.id.urtitle);
            urUnits=itemView.findViewById(R.id.urUnits);


        }
    }
}
