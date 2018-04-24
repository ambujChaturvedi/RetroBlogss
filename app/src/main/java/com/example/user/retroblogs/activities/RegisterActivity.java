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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    TextView mName;
    TextView mEmail;
    TextView mPassword;
    FloatingTextButton mcreateaccount;
    FirebaseAuth firebaseAuth;
    DatabaseReference mdatabaseRef;
    String name;
    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mName=(EditText)findViewById(R.id.name);
        mEmail=(EditText)findViewById(R.id.email);
        mPassword=(EditText)findViewById(R.id.password);
        mcreateaccount=(FloatingTextButton) findViewById(R.id.register);


        // firebase
        firebaseAuth= FirebaseAuth.getInstance();
             mdatabaseRef= FirebaseDatabase.getInstance().getReference().child("users");

        // create account button
        mcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(RegisterActivity.this, "button pressed", Toast.LENGTH_SHORT).show();


              name  =mName.getEditableText().toString();
                Log.d(TAG, "onClick: "+name);
               Email=mEmail.getEditableText().toString();

               String Password=mPassword.getEditableText().toString();
              signUpUser(Email,Password);

            }
        });


    }

    public void signUpUser(final String Email, String Password){
        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Log.d(TAG, "onComplete: user signedup");

                    // get the user unique id
                    String user_id=firebaseAuth.getCurrentUser().getUid();

                    // write the user info to the database

                    DatabaseReference user_db=mdatabaseRef.child(user_id);
                    user_db.child("name").setValue(name);
                    user_db.child("email").setValue(Email);
                    user_db.child("profilePic").child("defaultPic");
                    Toast.makeText(RegisterActivity.this, "Signing Up Sucessful", Toast.LENGTH_SHORT).show();

                    // start login Activity
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "There is a problem signing up", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: error siging up");
                }

            }
        });
    }
}
