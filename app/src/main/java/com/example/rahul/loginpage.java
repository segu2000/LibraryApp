package com.example.rahul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginpage extends AppCompatActivity {
    EditText lnemail,lnpassword;
    TextView btnlogin,signup;
    FirebaseAuth firebaseAuth;
    ProgressDialog mloadingbar;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        firebaseAuth = FirebaseAuth.getInstance();
        mloadingbar = new ProgressDialog(loginpage.this);


        lnemail = findViewById(R.id.ln_email);
        lnpassword = findViewById(R.id.ln_password);
        btnlogin = findViewById(R.id.btn_Login);
        signup = findViewById(R.id.lnsign_up);

        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.usertype,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginpage.this, Registrationpage.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lnemail.getText().toString().trim();
                String password = lnpassword.getText().toString();
                String item=spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(loginpage.this, "Enter the valid Data", Toast.LENGTH_LONG).show();
                }else if(email.equals("admin@gmail.com")&&password.equals("admin12345")&&item.equals("Admin")){
                    Intent intent=new Intent(loginpage.this,AdminHome.class);
                    startActivity(intent);
                    finish();
                }
                else if (!email.endsWith("@gmail.com")) {
                    lnemail.setError("invalid Email");
                    Toast.makeText(loginpage.this, "invalid Email", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    lnpassword.setError("invalid Password");
                    Toast.makeText(loginpage.this, "Please Enter the valid Password", Toast.LENGTH_LONG).show();
                } else if(item.equals("User")){
                    mloadingbar.setTitle("Login");
                    mloadingbar.setMessage("please wait");
                    mloadingbar.setCanceledOnTouchOutside(false);
                    mloadingbar.show();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(loginpage.this, UserHome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                mloadingbar.dismiss();
                            } else {
                                Toast.makeText(loginpage.this, "error in login page ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(loginpage.this, "error in login page", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


