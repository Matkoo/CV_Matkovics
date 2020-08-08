package matko.cv.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;


import bbraun.matko.cv.R;
import matko.cv.dbhelper.PlaceDatabase;
import matko.cv.helper.LocaleHelper;
import matko.cv.model.Place;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

@EFragment(R.layout.fragment_open)
public class OpenFragment extends Fragment implements OnMapReadyCallback {

    protected MapView mapView;

    private GoogleMap map;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        System.out.println("Run on map ready");

        MapsInitializer.initialize(getContext());

        map = googleMap;

        LatLng  aurora = new LatLng(47.493549,19.076620);
        map.addMarker(new MarkerOptions().position(aurora).title("Auróra 14"));
        map.moveCamera(CameraUpdateFactory.newLatLng(aurora));

    }
}
