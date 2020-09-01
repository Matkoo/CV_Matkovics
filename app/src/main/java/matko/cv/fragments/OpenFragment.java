package matko.cv.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


import matko.cv.R;
import matko.cv.dbhelper.PlaceDatabase;
import matko.cv.helper.LocaleHelper;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

@EFragment(R.layout.fragment_open)
public class OpenFragment extends Fragment {



    @ViewById(R.id.btHun)
    protected Button btHun;

    @ViewById(R.id.btEng)
    protected Button btEng;

    private PlaceDatabase placeDatabase;



    @Click(R.id.btEng)
    public void clickEng() {
        System.out.println("Button was clicked");
        LocaleHelper.setLocale(getContext(),"en");
    }





}
