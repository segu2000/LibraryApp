package com.example.rahul;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class myadapter extends FirebaseRecyclerAdapter<ListData,myadapter.myviewholder> {
    public myadapter(@NonNull FirebaseRecyclerOptions<ListData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ListData listData) {
        holder.bookid.setText(listData.getId());
        holder.Title.setText(listData.getTitle());
        holder.Author.setText(listData.getAuthor());
        holder.Units.setText(listData.getUnits());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final DialogPlus dialogPlus= DialogPlus.newDialog(holder.bookid.getContext())
                       .setContentHolder(new ViewHolder(R.layout.update_dialog))
                       .setExpanded(true,1100)
                       .create();
               View myview=dialogPlus.getHolderView();
                EditText myid=myview.findViewById(R.id.dID);
                EditText title=myview.findViewById(R.id.dtitle);
                EditText author=myview.findViewById(R.id.dAuthor);
                EditText units=myview.findViewById(R.id.dUnits);
                Button submit=myview.findViewById(R.id.bupdate);

                myid.setText(listData.getId());
                title.setText(listData.getTitle());
                author.setText(listData.getAuthor());
                units.setText(listData.getUnits());
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("Id",myid.getText().toString());
                        map.put("title",title.getText().toString());
                        map.put("Author",author.getText().toString());
                        map.put("Units",units.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Books")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(myview.getContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(myview.getContext(),"Not Updated ",Toast.LENGTH_LONG).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.Title.getContext());
                builder.setTitle("DELETE PANEL");
                builder.setMessage("Delete....?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Books")
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
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView Title,Author,Units,bookid;
        ImageView edit,delete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            bookid=itemView.findViewById(R.id.bookid);
            Author=itemView.findViewById(R.id.Author);
            Title=itemView.findViewById(R.id.title);
            Units=itemView.findViewById(R.id.Units);

            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);

        }
    }


}
