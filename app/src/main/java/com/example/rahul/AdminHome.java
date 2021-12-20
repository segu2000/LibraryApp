package com.example.rahul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdminHome extends AppCompatActivity {
    TextView Addbook,issuedbooks,ViewBook,issuebook,Adminlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Addbook=findViewById(R.id.Addbook);
        ViewBook=findViewById(R.id.Viewbook);
        issuebook=findViewById(R.id.issuedbook);
        issuedbooks=findViewById(R.id.issuedbooks);
        Adminlogout=findViewById(R.id.Adminlogout);
        Adminlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,loginpage.class));
                Toast.makeText(AdminHome.this,"Logot successfully",Toast.LENGTH_LONG).show();
            }
        });

        issuebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,Adminissuedbook.class));
            }
        });

        issuedbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,Admin_issuedbooks.class));
            }
        });

        ViewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,ViewBooks.class));
            }
        });



        Addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this,AddBook.class));
            }
        });
    }
}