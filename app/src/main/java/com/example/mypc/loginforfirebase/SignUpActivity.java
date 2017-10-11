package com.example.mypc.loginforfirebase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by vitinhHienAnh on 10-10-17.
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText edEmailSignUp, edPasswordSignUp;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup_user);
        edEmailSignUp = (EditText) findViewById(R.id.edEmailSignUp);
        edPasswordSignUp = (EditText) findViewById(R.id.edPasswordSignUp);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        String email = edEmailSignUp.getText().toString();
        String password = edPasswordSignUp.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                     }else {
                         Toast.makeText(SignUpActivity.this, "Could not register... please try again", Toast.LENGTH_SHORT).show();
                     }
            }
        });
    }
}
