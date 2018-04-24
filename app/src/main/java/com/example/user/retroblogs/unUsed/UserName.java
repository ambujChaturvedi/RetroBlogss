package com.example.user.retroblogs.unUsed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.user.retroblogs.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by user on 17-02-2018.
 */

 public class UserName extends AppCompatActivity{

    private static final String TAG = "UserName";

    // firebase stuff to retrive data
    FirebaseAuth mauth;
    DatabaseReference databaseReference;
    FirebaseUser mCureentUser;

    ProgressBar progressBar;
    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);


       textView=findViewById(R.id.user_name);
       textView2=findViewById(R.id.user_email);
       progressBar=findViewById(R.id.progressbar);

       // firebase intialize
        mauth=FirebaseAuth.getInstance();
        mCureentUser=mauth.getCurrentUser();
        progressBar.setVisibility(View.VISIBLE);
     databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(mCureentUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name= (String) dataSnapshot.child("name").getValue();
                String Email= (String) dataSnapshot.child("email").getValue();

                textView.setText(name);
                textView2.setText(Email);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}
