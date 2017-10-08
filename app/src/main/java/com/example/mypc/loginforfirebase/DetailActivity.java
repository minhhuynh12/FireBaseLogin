package com.example.mypc.loginforfirebase;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import adapter.DetailAdapter;
import items.UserItems;

/**
 * Created by vitinhHienAnh on 08-10-17.
 */

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    DataSnapshot dataSnapshot;
    TextView txtTest;
    DetailAdapter mAdapter;

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_user);
        Log.d("fffffff" , "value " + dataSnapshot);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycDetail);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid().toString();
        Log.d("dsdsdsdsdsdsd", "value: " + userID);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };


       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Log.d("eeeeeeee" , "connect" + dataSnapshot);
               showData(dataSnapshot);
//               showArray();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });


    }

    private void showArray(){
        ArrayList<UserItems> itemses = new ArrayList<>();
        itemses.add(new UserItems("minh" , " huynh" , "cong"));
        itemses.add(new UserItems("minh" , " huynh" , "cong"));
        itemses.add(new UserItems("minh" , " huynh" , "cong"));
        itemses.add(new UserItems("minh" , " huynh" , "cong"));
        RecyclerView.LayoutManager linearlayout = new LinearLayoutManager(DetailActivity.this);
        mRecyclerView.setLayoutManager(linearlayout);
        mAdapter = new DetailAdapter(itemses);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.equals(userID)){
                Log.d("wwwwww", "connect " + userID);
            }
            UserItems uInfo = new UserItems();
            uInfo.setName(ds.getValue(UserItems.class).getName().toString()); //set the name
            uInfo.setEmail(ds.getValue(UserItems.class).getEmail().toString()); //set the name
            uInfo.setPhone_num(ds.getValue(UserItems.class).getPhone_num().toString()); //set the name
            Log.d("ttttttt", "showData: name: " + userID);
//            uInfo.setEmail(ds.child(userID).getValue(UserItems.class).getEmail()); //set the email
//            uInfo.setPhone_num(ds.child(userID).getValue(UserItems.class).getPhone_num()); //set the phone_num

            //display all the information
            Log.d("hhhhhh", "showData: name: " + uInfo.getName());
            Log.d(TAG, "showData: email: " + uInfo.getEmail());
            Log.d(TAG, "showData: phone_num: " + uInfo.getPhone_num());

            ArrayList<UserItems> array  = new ArrayList<>();
            array.add(new UserItems(uInfo.getName(), uInfo.getEmail() , uInfo.getPhone_num()));
            array.add(new UserItems(uInfo.getName(), uInfo.getEmail() , uInfo.getPhone_num()));
            array.add(new UserItems(uInfo.getName(), uInfo.getEmail() , uInfo.getPhone_num()));
//            array.add(uInfo.getEmail());
//            array.add(uInfo.getPhone_num());
//            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
//            mListView.setAdapter(adapter);


            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new DetailAdapter(array);
            mRecyclerView.setAdapter(mAdapter);
        }
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


    private void toastMessage(String message) {
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show();

    }


}
