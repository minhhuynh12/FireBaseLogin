package com.example.mypc.loginforfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button btnLogin, btnAddItems, btnViewUser, btnSignUpUser;
    EditText editEmail , editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnViewUser = (Button) findViewById(R.id.btnViewUser);
        btnAddItems = (Button) findViewById(R.id.btnAddItems);
        btnSignUpUser = (Button) findViewById(R.id.btnSignUpUser);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null){
//            finish();
//            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
//        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toast("Successfully signed in with:" + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                if(!email.equals("")&& !password.equals("")){
                    mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                            }else {
                                toast("your user or password wrong");
                            }

                        }
                    });
                }else {
                    toast("you didn't fill ");
                }
            }


        });
        btnAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , AddToDataBase.class));
            }
        });
        btnViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });
        btnSignUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void toast(String message) {
        Toast.makeText(this , message , Toast.LENGTH_SHORT ).show();
    }
}
