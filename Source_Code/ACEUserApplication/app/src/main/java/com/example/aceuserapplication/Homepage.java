package com.example.aceuserapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aceuserapplication.databinding.ActivityHomepageBinding;
import com.example.aceuserapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Homepage extends AppCompatActivity {

    ActivityHomepageBinding binding;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    int count =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());


        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigation_view);

        toggle=new ActionBarDrawerToggle(this,drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_developer:
                        Toast.makeText(Homepage.this,"Developer",Toast.LENGTH_SHORT).show();
                        showDevelopers();
                        break;
                    case R.id.navigation_rate:
                        Toast.makeText(Homepage.this,"Rate",Toast.LENGTH_SHORT).show();
                        showRateDialog();
                        break;
                    case R.id.navigation_website:
                        Toast.makeText(Homepage.this,"Website",Toast.LENGTH_SHORT).show();
                        showsite();
                        break;
                    case R.id.navigation_share:
                        Toast.makeText(Homepage.this,"Share",Toast.LENGTH_SHORT).show();
                        showShare();
                        break;
                    case R.id.navigation_theme:
                        Toast.makeText(Homepage.this,"Dark Theme",Toast.LENGTH_SHORT).show();
                        changeTheme();
                        break;
                }
                return true;
            }
        });


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                 case R.id.navigation_home:
                     replaceFragment(new HomeFragment());
                    break;

                case R.id.navigation_gallery:
                    replaceFragment(new GalleryFragment());
                    break;

                case R.id.navigation_notice:
                    replaceFragment(new NoticeFragment());
                    break;

                case R.id.navigation_faculty:
                    replaceFragment(new EventsFragment());
                    break;

                case R.id.navigation_about:
                    replaceFragment(new ProfileFragment());
                    break;

            }

            return true;
        });

    }

    private void changeTheme() {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme
            //setTheme(R.style.Theme_ACEUserApplicationDark);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout , fragment);
        fragmentTransaction.commit();

    }

    private void showDevelopers() {

        Developers developers = new Developers();
        developers.show(getSupportFragmentManager(),"tag");

    }

    private void showShare() {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String body = "Check out our KLE ACE APP https://www.klescet.ac.in/ ";
        String sub = "Check out our ACE app https://www.klescet.ac.in/";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
        myIntent.putExtra(Intent.EXTRA_TEXT,body);
        startActivity(Intent.createChooser(myIntent, "Share Using"));
    }

    private void showsite() {
        String url = "https://www.klescet.ac.in/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void showRateDialog() {
        RateDialog rateDialog = new RateDialog();
        rateDialog.show(getSupportFragmentManager(),"tag");
    }



}