package com.example.user.retroblogs.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.retroblogs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    TextView create_account;
    EditText email;
    EditText password;
    FloatingTextButton login_btn;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login_btn=findViewById(R.id.login);
        create_account=findViewById(R.id.create_account);

            // Firebase Stuff
        mAuth=FirebaseAuth.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users");

         create_account.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                 startActivity(intent);
             }
         });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString();
                String Password=password.getText().toString();

                loginUser(Email,Password);
            }
        });

    }

    private void loginUser(String email, String password) {
        if(email.isEmpty() && password.isEmpty()){
            if (email.isEmpty() || password.isEmpty() ){
                Toast.makeText(this, "Please Fill All the Feild Correctly", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                      checkUserExist();
                        Toast.makeText(LoginActivity.this, "Sign In Sucessful", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(LoginActivity.this, "Error Sigining", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onComplete: Error c");
                    }

                }

            });

        }
    }

    private void checkUserExist() {
        final String user_id=mAuth.getCurrentUser().getUid();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
