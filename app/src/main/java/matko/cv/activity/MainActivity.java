package matko.cv.activity;


import android.os.Bundle;
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
import java.nio.charset.Charset;
import java.util.List;

import matko.cv.R;
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

    private ActionBar actionBar;




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


        //set the user agent to OSM
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        actionBar = getSupportActionBar();

        actionBar.setTitle("Matkovics");
        actionBar.setHomeAsUpIndicator(R.drawable.more);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!drawer_layout.isDrawerOpen(GravityCompat.START)){
            actionBar.setHomeAsUpIndicator(R.drawable.list);
            drawer_layout.openDrawer(GravityCompat.START);

        }
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START);
            actionBar.setHomeAsUpIndicator(R.drawable.more);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, openFragment).commit();

        dbUpload();
    }



    @Background
    public void dbUpload() {

        placeDatabase = PlaceDatabase.getInstance(this);
        
        List<Place> placeList = placeDatabase.latleletAzonositoDAO().getAllPlace();

        for (Place item: placeList) {
            placeDatabase.latleletAzonositoDAO().deletePlace(item);
        }

        InputStream is = getResources().openRawResource(R.raw.matkovics_gergely_cv_db);

        String line = "";

        int count = 0;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));) {

            while ((line = reader.readLine()) != null) {
                if (count > 0 ) {
                    String[] split = line.split(",");;
                    placeDatabase.latleletAzonositoDAO().insertPlace(new Place(split[1], split[2], split[3],split[4], split[5], split[6],Integer.parseInt(split[7])));
                }
                count++;
            }
        }catch (IOException e){

        }

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