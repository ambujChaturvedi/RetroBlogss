package com.example.user.retroblogs.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.retroblogs.R;
import com.example.user.retroblogs.fragments.FragmentAdapter;
import com.example.user.retroblogs.fragments.Tab1Fragment;
import com.example.user.retroblogs.fragments.Tab2Fragment;
import com.example.user.retroblogs.unUsed.UserName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    FragmentAdapter fragmentAdapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        ViewPager viewPager=(ViewPager) findViewById(R.id.container);
        setupViewpager(viewPager);

        mAuth=FirebaseAuth.getInstance();

        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorBlack));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Blogs");
        tabLayout.getTabAt(1).setText("Popular");


    }

    private void setupViewpager(ViewPager viewPager) {
        FragmentAdapter fragmentAdapter1=new FragmentAdapter(getSupportFragmentManager());
        fragmentAdapter1.addFragment(new Tab1Fragment());
        fragmentAdapter1.addFragment(new Tab2Fragment());
        viewPager.setAdapter(fragmentAdapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser==null){

            Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            mAuth.signOut();
            Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id==R.id.user){
            Intent intent=new Intent(HomeActivity.this,UserName.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
