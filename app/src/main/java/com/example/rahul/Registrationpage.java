package com.example.rahul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrationpage extends AppCompatActivity {
    EditText myname,myemail,pass,cpass;
    TextView btn_signup,btn_signin;
    FirebaseAuth firebaseAuth;

    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);
        firebaseAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(Registrationpage.this);

        myname = findViewById(R.id.Regname);
        myemail = findViewById(R.id.RegEmail);
        pass = findViewById(R.id.Regpassword);
        cpass = findViewById(R.id.Regcpassword);
        btn_signup = findViewById(R.id.Register);
        btn_signin = findViewById(R.id.signIn);


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registrationpage.this,loginpage.class);
                startActivity(intent);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = myname.getText().toString();
                String email = myemail.getText().toString().trim();
                String password = pass.getText().toString();
                String cpassword = cpass.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(Registrationpage.this, "Please Enter the valid Data", Toast.LENGTH_LONG).show();
                } else if(!email.endsWith("@gmail.com")){
                    myemail.setError("Please Enter the valid Email");
                    Toast.makeText(Registrationpage.this, "Please Enter the valid Email", Toast.LENGTH_LONG).show();
                } else if (password.length() < 6) {
                    pass.setError("invalid Password");
                    Toast.makeText(Registrationpage.this, "Atleast 6 characters", Toast.LENGTH_LONG).show();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(Registrationpage.this, "Password does not match", Toast.LENGTH_LONG).show();

                } else  {
                    mLoadingBar.setTitle("Registration");
                    mLoadingBar.setMessage("Please wait");
                    mLoadingBar.setCanceledOnTouchOutside(false);
                    mLoadingBar.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registrationpage.this, "User Created successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Registrationpage.this, UserHome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                mLoadingBar.dismiss();
                            } else {
                                Toast.makeText(Registrationpage.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

}