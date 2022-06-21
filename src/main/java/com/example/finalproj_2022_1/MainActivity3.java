package com.example.finalproj_2022_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.MapFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity3 extends AppCompatActivity {
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    FragmentManager fragmentManager;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    MainFragment mainFragment;
    LocationFragment locationFragment;
    WeatherFragment weatherFragment;
    MapsFragment mapFragment;
    GameFragment gameFragment;
    MemberFragment2 memberFragment2;
    AudioFragment audioFragment;

    View navHeader; //네비게이션 드로우어 헤더
    NavigationView navigationView;
    SharedPreferences preferences;

    TextView tvUname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DrawerLayout
        drawer = (DrawerLayout) findViewById(R.id.main_drawer);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //navigation toggle home
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.d_open, R.string.d_close);
        toggle.syncState();

        //Fragment
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        mainFragment = new MainFragment();
        locationFragment = new LocationFragment();
        weatherFragment = new WeatherFragment();
        mapFragment = new MapsFragment();
        gameFragment = new GameFragment();
        memberFragment2 = new MemberFragment2();
        audioFragment = new AudioFragment();

        //Fragment 배치
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.container, mainFragment, null);
        fragmentTransaction.commit();

        //NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //NavigationView Header
        navHeader = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                if (id == R.id.menu_home) {
                    fragmentTransaction.replace(R.id.container, mainFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_login) {
                    fragmentTransaction.replace(R.id.container, loginFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.menu_register) {
                    fragmentTransaction.replace(R.id.container, registerFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Register", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_location) {
                    fragmentTransaction.replace(R.id.container, locationFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Location", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.menu_weather) {
                    fragmentTransaction.replace(R.id.container, weatherFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Weather", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.menu_game){
                    fragmentTransaction.replace(R.id.container, gameFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Game", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.menu_schedule){
                    fragmentTransaction.replace(R.id.container, memberFragment2, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Member", Toast.LENGTH_SHORT).show();
                }else if(id==R.id.menu_audio){
                    fragmentTransaction.replace(R.id.container, audioFragment, null);
                    fragmentTransaction.commit();
                    Toast.makeText(getApplicationContext(), "Music", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawer(Gravity.LEFT, true);
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {  //navigation toggle
            //SharedPreference
            preferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
            String uName = preferences.getString("name", "");
            //NavigationDrawer Header의 텍스트 값 변경
            tvUname = (TextView) navHeader.findViewById(R.id.u_name);
            tvUname.setText(uName + "님");
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}