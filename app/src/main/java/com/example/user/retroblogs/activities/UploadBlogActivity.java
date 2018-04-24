package com.example.user.retroblogs.activities;
import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.user.retroblogs.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class UploadBlogActivity extends AppCompatActivity {

    EditText title;
    EditText descrep;
    ImageView image_selector;
    FloatingTextButton upload_btn;
    final static int REQUEST_CODE = 123;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

       //for reteriving udernanme
    DatabaseReference mdatabaseref;
    FirebaseUser mUser;
    FirebaseAuth mAuth;

    Uri imageuri = null;
    private static final String TAG = "UploadBlogActivity";
    private final static int REQUEST_CODE_Gallery = 1;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.fragment3_layout);

        super.onCreate(savedInstanceState);
        title = findViewById(R.id.title);
        descrep = findViewById(R.id.des_creption);
        image_selector = findViewById(R.id.select_image);
        upload_btn = findViewById(R.id.post);
        progressBar=findViewById(R.id.progressbar);



        image_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: button");
                // check for runtime permission
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(UploadBlogActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                    }

                } else {
                    callGalleryforImage();
                }
            }
        });

        // intialising FireBase Stuff
        storageReference = FirebaseStorage.getInstance().getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");
        //
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mdatabaseref=FirebaseDatabase.getInstance().getReference().child("users").child(mUser.getUid());


   // on create ends

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                 String Title=title.getText().toString();
                 String DesCrption=descrep.getText().toString();
               uploadDataOnDatabase(Title,DesCrption);

            }
        });
    }


    private void uploadDataOnDatabase(final String title, final String desCrption) {

        StorageReference filePath = storageReference.child("blogImages").child(imageuri.getLastPathSegment());
          filePath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  final Uri download_uri=taskSnapshot.getDownloadUrl();
                  final DatabaseReference newPost=databaseReference.push();

                  mdatabaseref.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {

                          newPost.child("title").setValue(title);
                          newPost.child("description").setValue(desCrption);
                          newPost.child("imageUrl").setValue(download_uri.toString());
                          newPost.child("UID").setValue(mUser.getUid());
                          newPost.child("username").setValue(dataSnapshot.child("name").getValue());
                          newPost.child("date").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {
                                  if(task.isComplete()){
                                      progressBar.setVisibility(View.GONE);
                                      Intent intent=new Intent(UploadBlogActivity.this,HomeActivity.class);
                                      startActivity(intent);
                                     Toast.makeText(UploadBlogActivity.this, "Upload SucessFull", Toast.LENGTH_SHORT).show();

                                  }

                              }
                          });



                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                      }
                  });

              }
          });




    }


    private void callGalleryforImage() {
        Log.d(TAG, "callGalleryforImage: started");

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_Gallery);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: started" + requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted:");
                callGalleryforImage();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: started" + data.toString());
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_Gallery && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult:after result code " + requestCode);

            imageuri = data.getData();
            image_selector.setImageURI(imageuri);


            Picasso.with(UploadBlogActivity.this).load(imageuri).into(image_selector);
           // Toast.makeText(UploadBlogActivity.this, "Upload Sucessful", Toast.LENGTH_SHORT).show();
        }


    }

}