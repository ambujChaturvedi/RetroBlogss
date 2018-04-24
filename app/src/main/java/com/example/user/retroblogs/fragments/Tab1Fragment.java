package com.example.user.retroblogs.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.retroblogs.R;
import com.example.user.retroblogs.activities.UploadBlogActivity;
import com.example.user.retroblogs.model.Post;
import com.example.user.retroblogs.unUsed.DateAndTime;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 10-02-2018.
 */

public class Tab1Fragment  extends Fragment {

    private static final String TAG = "Tab1Fragment";
    private ImageView fab;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private boolean processLke=false;
    FirebaseAuth mAuth;
    String post_key;


    //firebase
    DatabaseReference databaseReference;
    DatabaseReference datalikeRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1_layout,container,false);
        fab=view.findViewById(R.id.fab);
        progressBar=view.findViewById(R.id.pd);




        recyclerView=view.findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), UploadBlogActivity.class);
                startActivity(intent);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference().child("posts");

        mAuth=FirebaseAuth.getInstance();

        return  view;

    }






    @Override
    public void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);

        FirebaseRecyclerAdapter<Post,PostViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class,R.layout.recycleview_row_layout,
                        PostViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {

                 post_key=getRef(position).getKey();

                //datalikeRef=FirebaseDatabase.getInstance().getReference().child("Likes");
               // databaseReference=FirebaseDatabase.getInstance().getReference().child(post_key ).child(mAuth.getCurrentUser().getUid());

                // this method popluate the view like onBind ViewHolder
                viewHolder.setDesc(model.getDescription());
                viewHolder.setImageUrl(getContext(),model.getImageUrl());
                viewHolder.setTitle(model.getTitle());
                viewHolder.setUserName(model.getUsername());
                viewHolder.setdateAndTime(model.getDate());

                //viewHolder.like_cnt.setText("21");

                    viewHolder.like_BTN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           // onStarClicked(databaseReference);
                        }
                    });





                progressBar.setVisibility(View.GONE);


            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

      /*  private void onStarClicked(DatabaseReference postRef) {
            postRef.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(MutableData mutableData) {
                    Post p = mutableData.getValue(Post.class);
                    if (p == null) {
                        return Transaction.success(mutableData);
                    }

                    if (p.stars.containsKey(mAuth.getCurrentUser().getUid())) {
                        // Unstar the post and remove self from stars
                        p.starCount = p.starCount - 1;
                        p.stars.remove(mAuth.getCurrentUser().getUid());
                    } else {
                        // Star the post and add self to stars
                        p.starCount = p.starCount + 1;
                        p.stars.put(mAuth.getCurrentUser().getUid(), true);
                    }

                    // Set value and report transaction success
                    mutableData.setValue(p);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(DatabaseError databaseError, boolean b,
                                       DataSnapshot dataSnapshot) {
                    // Transaction completed
                    Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                }
            });
        }
*/





    //----------------------------------------------------------------------------------------------------------------------------
    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView like_cnt;
        ImageView like_BTN;
        public PostViewHolder(View itemView) {
            super(itemView);
            View mView=itemView;
           like_cnt=itemView.findViewById(R.id.like_cnt);
            like_BTN=itemView.findViewById(R.id.like_btn);


        }

        public void setDesc(String desc){
            TextView post_desc=itemView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }
        public void setImageUrl(Context context, String imageUrl){
            ImageView image=itemView.findViewById(R.id.main_image);
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.circle_icon).into(image);
        }
        public void setTitle(String title){
            TextView post_title=itemView.findViewById(R.id.post_title);
            post_title.setText(title);
        }
        public void setUserName(String userName){
            TextView user_name=itemView.findViewById(R.id.user_name);
            user_name.setText(userName);
        }
        public void setdateAndTime(long date){
          // String Date_time= DateAndTime.getTimeDate(date);
           RelativeTimeTextView t1=itemView.findViewById(R.id.timestamp);
           t1.setReferenceTime(date);
        }

    }
    //------------------------------------------------------------------------------------------------------------------------------
}

