package com.example.rahul;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class adminissueadapter extends FirebaseRecyclerAdapter<ListData,adminissueadapter.viewholder>  {
    public adminissueadapter(@NonNull FirebaseRecyclerOptions<ListData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder holder, int position, @NonNull ListData listData) {
        holder.userTitle.setText(listData.getTitle());
        holder.username.setText(listData.getUsername());
        holder.userphone.setText(listData.getPhoneno());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.username.getContext())
                        .setContentHolder(new ViewHolder(R.layout.returndate))
                        .setExpanded(true,1500)
                        .create();
                View myview=dialogPlus.getHolderView();
               TextView Rtitle=myview.findViewById(R.id.Rtitle);
               TextView Rusername=myview.findViewById(R.id.Rusername);
                TextView Rphoneno=myview.findViewById(R.id.Rphoneno);
                EditText date=myview.findViewById(R.id.date_time);
                EditText txtsms=myview.findViewById(R.id.txtmessage);

                TextView currentDate=myview.findViewById(R.id.currentdate);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
                String currentDateandTime = sdf.format(new Date());
                currentDate.setText(currentDateandTime);

                Rtitle.setText(listData.getTitle());
                Rusername.setText(listData.getUsername());
                Rphoneno.setText(listData.getPhoneno());
                Button submit=myview.findViewById(R.id.Returndate);
                dialogPlus.show();
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // calender class's instance and get current date , month and year from calender
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR); // current year
                        int mMonth = c.get(Calendar.MONTH); // current month
                        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                        // date picker dialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(myview.getContext(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // set day of month , month and year value in the edit text
                                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ContextCompat.checkSelfPermission(myview.getContext(), Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {
                            String Sms = txtsms.getText().toString().trim();
                            String phoneNo = Rphoneno.getText().toString().trim();
                            if (!Sms.equals("")&&!phoneNo.equals("")){
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo, null, Sms, null, null);
                                Toast.makeText(myview.getContext(), "Message is sent", Toast.LENGTH_LONG).show();
                            }
                          else {
                                Toast.makeText(myview.getContext(), "Faild to send message", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            ActivityCompat.requestPermissions((Activity) myview.getContext(),new String[]{Manifest.permission.SEND_SMS}
                            ,100);
                        }
                        Map<String,Object> map=new HashMap<>();

                        map.put("Returndate",date.getText().toString());
                        map.put("currentdate",currentDate.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("LibBooks")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.issuedatalist,parent,false);
        return new viewholder(view);
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView userTitle,username,userphone;
        public viewholder(@NonNull View itemView) {
            super(itemView);
           userTitle=itemView.findViewById(R.id.userbooktitle);
           username=itemView.findViewById(R.id.newusername);
            userphone=itemView.findViewById(R.id.userphoneno);



        }
    }
}
