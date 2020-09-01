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


@EFragment(R.layout.fragment_schools)
public class SchoolsFragment extends Fragment implements PlaceRecyclerAdapter.OnPlaceListener, OnMapReadyCallback {


    protected MapView mapView;

    private GoogleMap map;

    private List<Place> schoolList = new ArrayList<>();

    @ViewById(R.id.recSchooList)
    protected RecyclerView recSchooList;

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

        schoolList = placeDatabase.latleletAzonositoDAO().getAllSchool();
        adapter = new PlaceRecyclerAdapter(schoolList,getContext(),this);
        recSchooList.setAdapter(adapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recSchooList.setLayoutManager(layoutManager);

    }

    @Override
    public void onPlaceClick(int position) {

        map.clear();
        Place slectedPlace = schoolList.get(position);
        LatLng school = new LatLng(Double.parseDouble(slectedPlace.getLatitude()),Double.parseDouble(slectedPlace.getLongitude()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(school,17.0f));
        map.addMarker(new MarkerOptions().position(school));

        System.out.println(schoolList.get(position).getPlaceName()+" Here a click");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapSchool);

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

        LatLng tatabanya = new LatLng(47.586831,18.397964);
        map.addMarker(new MarkerOptions().position(tatabanya));
        map.moveCamera(CameraUpdateFactory.newLatLng(tatabanya));
        map.animateCamera(CameraUpdateFactory.zoomTo(13.0f));

    }

}


