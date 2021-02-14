package com.example.parkingapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.parkingapp.R;
import com.example.parkingapp.common.PreferenceSettings;
import com.example.parkingapp.model.User;
import com.example.parkingapp.viewmodels.UserViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private PreferenceSettings mPreferenceSettings;
    private DrawerLayout drawer;
    private UserViewModel userViewModel;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferenceSettings = new PreferenceSettings(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.userViewModel = UserViewModel.getInstance();

        if (mPreferenceSettings.getUserID() == null || mPreferenceSettings.getUserID() == "") {
            this.userID = this.userViewModel.getUserRepository().loggedInUserID.getValue();
            Log.e("userID", userID);
            mPreferenceSettings.setUserID(userID);
        } else {
            userID = mPreferenceSettings.getUserID();
        }
        this.drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        View header = navigationView.getHeaderView(0);
        TextView tvUsername = header.findViewById(R.id.tvUsername);
        TextView tvUserEmail = header.findViewById(R.id.tvUserEmail);

        this.userViewModel.getUserByID(userID);
        this.userViewModel.getUserRepository().userData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tvUsername.setText(user.getFirstName() + " " + user.getLastName());
                tvUserEmail.setText(user.getEmail());
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_add_parking, R.id.nav_updateProfile)
                .setDrawerLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Logout").setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userViewModel.getUserRepository().signInStatus.postValue("");
                        mPreferenceSettings.logoutUser();
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}