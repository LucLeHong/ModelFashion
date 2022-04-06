package com.example.modelfashion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.modelfashion.Fragment.CartFragment;
import com.example.modelfashion.Fragment.CategoryFragment;
import com.example.modelfashion.Fragment.FragmentProfile;
import com.example.modelfashion.Fragment.MainFragment;
import com.example.modelfashion.Model.response.User.User;
import com.example.modelfashion.R;
import com.example.modelfashion.Utility.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String user_id;
    Boolean isLogin;
    Bundle info;
    public static BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = new Bundle();
        getUserData();
        info.putString("user_id",user_id);
        replaceFragment(new MainFragment());
        navigationView=findViewById(R.id.bottom_navigation_view_linear);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_item_home:
                        MainFragment mainFragment = new MainFragment();
                        mainFragment.setArguments(info);
                        replaceFragment(mainFragment);
                        break;
                    case R.id.main_item_cart:
                        CartFragment cartFragment = new CartFragment();
                        cartFragment.setArguments(info);
                        replaceFragment(cartFragment);
                        break;
                    case R.id.main_item_category:
                        CategoryFragment categoryFragment = new CategoryFragment();
                        categoryFragment.setArguments(info);
                        replaceFragment(categoryFragment);
                        break;
                    case R.id.main_item_profile:
                        FragmentProfile fragmentProfile = new FragmentProfile();
                        fragmentProfile.setArguments(info);
                        replaceFragment(fragmentProfile);
                        break;
                }
                return true;
            }
        });
    }

    private void getUserData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.KEY_SAVE_USER, Context.MODE_MULTI_PROCESS);
        isLogin = sharedPreferences.getBoolean(Constants.KEY_CHECK_LOGIN, true);
        if (isLogin == false) {
//            User user = new User("", "", "", "", "", "", "");
//            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
//            prefsEditor.putString("user", user.toString());
//            prefsEditor.apply();
            user_id = "null";
        } else {
            if (sharedPreferences.contains(Constants.KEY_GET_USER)) {
                String userData = sharedPreferences.getString(Constants.KEY_GET_USER, "");
                try {
                    JSONObject obj = new JSONObject(userData);
                    user_id = obj.getString(Constants.KEY_ID);
                    Log.d("My App", obj.toString()+user_id);

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + userData + "\"");
                }
            }
        }
    }

    private void replaceFragment(Fragment fm){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fm.setArguments(info);
        fragmentTransaction.replace(R.id.frmlayout,fm);
        fragmentTransaction.commit();

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}