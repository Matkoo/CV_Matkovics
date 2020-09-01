package matko.cv.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import matko.cv.R;
import matko.cv.adapter.PlaceRecyclerAdapter;
import matko.cv.dbhelper.PlaceDatabase;
import matko.cv.model.Place;


/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

@EFragment(R.layout.fragment_experience)
public class ExperienceFragment extends Fragment implements PlaceRecyclerAdapter.OnPlaceListener, OnMapReadyCallback {

    protected MapView mapView;

    private GoogleMap map;

    private List<Place> expList = new ArrayList<>();

    @ViewById(R.id.recExpList)
    protected RecyclerView recExpList;

    private PlaceRecyclerAdapter adapter;

    private PlaceDatabase placeDatabase;

    @Override
    public void onStart() {
        super.onStart();

        intiView();

    }

    @Background
    protected void intiView() {
        placeDatabase = PlaceDatabase.getInstance(getContext());

        expList = placeDatabase.latleletAzonositoDAO().getAllJob();
        adapter = new PlaceRecyclerAdapter(expList,getContext(),this);
        recExpList.setAdapter(adapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recExpList.setLayoutManager(layoutManager);
        recExpList.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                System.out.println("Scroll x : "+scrollX);
                System.out.println("Scroll y : "+scrollY);

                System.out.println("oldScroll x : "+oldScrollX);
                System.out.println("oldScroll y : "+oldScrollY);


            }
        });
    }

    @Override
    public void onPlaceClick(int position) {
        map.clear();
        Place slectedPlace = expList.get(position);
        LatLng school = new LatLng(Double.parseDouble(slectedPlace.getLatitude()),Double.parseDouble(slectedPlace.getLongitude()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(school,17.0f));
        map.addMarker(new MarkerOptions().position(school));

        System.out.println(expList.get(position).getPlaceName()+" Here a click");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapExp);

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

        LatLng bpLocation = new LatLng(47.586831,18.397964);
        map.addMarker(new MarkerOptions().position(bpLocation));
        map.moveCamera(CameraUpdateFactory.newLatLng(bpLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

    }
}
