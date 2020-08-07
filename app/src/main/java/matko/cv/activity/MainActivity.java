package matko.cv.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import bbraun.matko.cv.R;
import matko.cv.fragments.OpenFragment;
import matko.cv.fragments.OpenFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    OpenFragment openFragment = new OpenFragment_();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,openFragment).commit();
    }
}