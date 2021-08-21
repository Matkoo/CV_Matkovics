package matko.cv.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
public class ExperienceFragment extends Fragment implements PlaceRecyclerAdapter.OnPlaceListener {

    private static final String TAG = "ExperienceFragment";

    @ViewById(R.id.viewpagerExperiences)
    protected ViewPager2 viewPager2;

    @ViewById(R.id.mapExp)
    protected MapView mapView;

    private List<Place> expList = new ArrayList<>();;

    private PlaceRecyclerAdapter adapter;

    private PlaceDatabase placeDatabase;

    private Place slectedPlace;

    @Override
    public void onStart() {
        super.onStart();
        initMap();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatabase();
    }

    private void initDatabase() {

        placeDatabase = PlaceDatabase.getInstance(getContext());
        placeDatabase.latleletAzonositoDAO().getAllJob().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Place>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Place> places) {

                        expList = new ArrayList<>(places);
                        initView();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "Error on database loading "+e );
                    }
                });

    }

    @UiThread
    protected void initMap() {

        if(mapView != null){
            mapView.onResume();

            mapView.setTileSource(TileSourceFactory.MAPNIK);
            mapView.setMultiTouchControls(true);


            //set the map to Tatabánya
            mapView.getController().setCenter(new GeoPoint( 47.560504585701146, 18.424900931157893));
            mapView.getController().setZoom(14.0);
            mapView.invalidate();

        }
    }

    @UiThread
    protected void initView() {
        //Set a last experience a first in the "timeline"
        Collections.reverse(expList);
        adapter = new PlaceRecyclerAdapter(expList, getContext(), this);
        viewPager2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        viewPager2.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    public void onPlaceClick(int position) {

        mapView.getOverlays().clear();
        mapView.invalidate();

        slectedPlace = expList.get(position);
        GeoPoint job = new GeoPoint(Double.parseDouble(slectedPlace.getLatitude()), Double.parseDouble(slectedPlace.getLongitude()));

        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(job);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        do nothing when click on marker
        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                return false;
            }
        });
        mapView.getOverlays().add(startMarker);


        mapView.getController().setCenter(job);
        mapView.getController().setZoom(17.0);
        mapView.invalidate();

    }
}
