package matko.cv.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import matko.cv.R;
import matko.cv.dbhelper.PlaceDatabase;
import matko.cv.fragments.ExperienceFragment;
import matko.cv.fragments.ExperienceFragment_;
import matko.cv.fragments.HobbysFragment;
import matko.cv.fragments.HobbysFragment_;
import matko.cv.fragments.LanguagesFragment;
import matko.cv.fragments.LanguagesFragment_;
import matko.cv.fragments.PersonalInfoFragment;
import matko.cv.fragments.PersonalInfoFragment_;
import matko.cv.fragments.SchoolsFragment;
import matko.cv.fragments.SchoolsFragment_;
import matko.cv.model.Place;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "MainActivity";

    @ViewById(R.id.nav_view)
    protected NavigationView navView;

    @ViewById(R.id.drawer_layout)
    protected DrawerLayout drawer_layout;

    private ActionBar actionBar;


    private final PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment_();
    private final ExperienceFragment experienceFragment = new ExperienceFragment_();
    private final HobbysFragment hobbysFragment = new HobbysFragment_();
    private final LanguagesFragment languagesFragment = new LanguagesFragment_();
    private final SchoolsFragment schoolsFragment = new SchoolsFragment_();

    private PlaceDatabase placeDatabase;


    @Override
    public void onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set the user agent to OSM
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);


        initActionBar();


    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setTitle("Matkovics Gergely");
        actionBar.setHomeAsUpIndicator(R.drawable.more);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    //Check the change in language after restart the activity
    @Override
    public void onConfigurationChanged(@NonNull android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.openDrawer(GravityCompat.START);

        }
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();


        //change navigation icon when drawer menu condition is changing
        drawer_layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
                if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    actionBar.setHomeAsUpIndicator(R.drawable.more);
                }
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    actionBar.setHomeAsUpIndicator(R.drawable.list);
                }

            }
        });


        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, personalInfoFragment).commit();

        dbUpload();
    }


    @Background
    public void dbUpload() {

        placeDatabase = PlaceDatabase.getInstance(this);

        List<Place> placeList = placeDatabase.latleletAzonositoDAO().getAllPlace();

        for (Place item : placeList) {
            placeDatabase.latleletAzonositoDAO().deletePlace(item);
        }

        InputStream is = getResources().openRawResource(R.raw.matkovics_gergely_cv_db);

        String line = "";

        int count = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            while ((line = reader.readLine()) != null) {
                if (count > 0) {
                    String[] split = line.split(",");
                    placeDatabase.latleletAzonositoDAO().insertPlace(new Place(split[1], split[2], split[3], split[4], split[5], split[6], Integer.parseInt(split[7])));
                }
                count++;
            }
        } catch (IOException e) {
            Log.e(TAG, "Exception by open the db file " + e.getMessage() );
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, personalInfoFragment).commit();
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