package matko.cv.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import bbraun.matko.cv.R;
import matko.cv.dbhelper.PlaceDatabase;
import matko.cv.fragments.ExperienceFragment;
import matko.cv.fragments.ExperienceFragment_;
import matko.cv.fragments.HobbysFragment;
import matko.cv.fragments.HobbysFragment_;
import matko.cv.fragments.LanguagesFragment;
import matko.cv.fragments.LanguagesFragment_;
import matko.cv.fragments.OpenFragment;
import matko.cv.fragments.OpenFragment_;
import matko.cv.fragments.SchoolsFragment;
import matko.cv.fragments.SchoolsFragment_;
import matko.cv.model.Place;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById(R.id.nav_view)
    protected NavigationView navView;

    @ViewById(R.id.drawer_layout)
    protected DrawerLayout drawer_layout;



    private OpenFragment openFragment = new OpenFragment_();
    private ExperienceFragment experienceFragment = new ExperienceFragment_();
    private HobbysFragment hobbysFragment = new HobbysFragment_();
    private LanguagesFragment languagesFragment = new LanguagesFragment_();
    private SchoolsFragment schoolsFragment = new SchoolsFragment_();



    private PlaceDatabase placeDatabase;


    @Override
    public void onBackPressed() {

        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();

        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, openFragment).commit();

        dbUpload();
    }

//    private void initView() {
//
//        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//
//                switch (menuItem.getItemId()) {
//                    case R.id.nav_home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, openFragment).commit();
//                    case R.id.nav_experience:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, experienceFragment).commit();
//                        System.out.println("Was clicked exper");
//                    case R.id.nav_hobbys:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, hobbysFragment).commit();
//                    case R.id.nav_languages:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, languagesFragment).commit();
//                    case R.id.nav_schools:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, schoolsFragment).commit();
//                }
//                return false;
//            }
//        });
//
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, schoolsFragment).commit();
//    }


    @Background
    public void dbUpload() {

        placeDatabase = PlaceDatabase.getInstance(this);

        placeDatabase.latleletAzonositoDAO().updatePlace(new Place("Árpád", "diák", "1997-2001", "23,234", "2314,123"));
        placeDatabase.latleletAzonositoDAO().updatePlace(new Place("Árpád", "diák", "1997-2001", "23,234", "2314,123"));
        placeDatabase.latleletAzonositoDAO().updatePlace(new Place("Árpád", "diák", "1997-2001", "23,234", "2314,123"));
        placeDatabase.latleletAzonositoDAO().updatePlace(new Place("Árpád", "diák", "1997-2001", "23,234", "2314,123"));
        placeDatabase.latleletAzonositoDAO().updatePlace(new Place("Árpád", "diák", "1997-2001", "23,234", "2314,123"));
        placeDatabase.latleletAzonositoDAO().updatePlace(new Place("Árpád", "diák", "1997-2001", "23,234", "2314,123"));

        List<Place> placeList = placeDatabase.latleletAzonositoDAO().getAllPlace();

        System.out.println(placeList.size() + " Db size ");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, openFragment).commit();
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_experience:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, experienceFragment).commit();
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_hobbys:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, hobbysFragment).commit();
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_languages:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, languagesFragment).commit();
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_schools:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, schoolsFragment).commit();
                drawer_layout.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }
}