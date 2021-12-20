package com.example.rahul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddBook extends AppCompatActivity {
    EditText Auth, Add_title,Add_Units,id;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Auth= findViewById(R.id.Author);
        Add_title = findViewById(R.id.Add_tile);
        button1 = findViewById(R.id.button1);
        Add_Units=findViewById(R.id.Add_Units);
        id=findViewById(R.id.Add_ID);


        AddData();

    }
    public void AddData() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> map=new HashMap<>();
                map.put("ID",id.getText().toString());
                map.put("title",Add_title.getText().toString());
                map.put("Author",Auth.getText().toString());
                map.put("Units",Add_Units.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Books").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                id.setText("");
                                Add_title.setText("");
                                Auth.setText("");
                                Add_Units.setText("");
                                Toast.makeText(getApplicationContext(),"ADDED SUCCESSFULLY ",Toast.LENGTH_LONG).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext()," NOT ADDED ",Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

    }

}
