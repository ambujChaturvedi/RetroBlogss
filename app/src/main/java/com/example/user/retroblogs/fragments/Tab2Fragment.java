package com.example.user.retroblogs.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.retroblogs.R;
import com.example.user.retroblogs.activities.RegisterActivity;

/**
 * Created by user on 10-02-2018.
 */

public class Tab2Fragment extends Fragment {
    TextView t1;
    private static final String TAG = "Tab2Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2_layout,container,false);

        t1=view.findViewById(R.id.textView2);
        t1.setText("Tap To Register");
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }
}
