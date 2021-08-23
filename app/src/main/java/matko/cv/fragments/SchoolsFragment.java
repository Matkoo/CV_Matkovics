package matko.cv.fragments;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
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


@EFragment(R.layout.fragment_schools)
public class SchoolsFragment extends Fragment implements PlaceRecyclerAdapter.OnPlaceListener {

    private static final String TAG = "SchoolsFragment";

    @ViewById(R.id.viewpagerSchools)
    protected ViewPager2 viewPager2;

    @ViewById(R.id.mapSchool)
    protected MapView mapView;

    private List<Place> schoolList = new ArrayList<>();

    private PlaceRecyclerAdapter adapter;

    private PlaceDatabase placeDatabase;

    private Place slectedPlace;

    @ViewById(R.id.forwardArrow)
    protected ImageView forwardArrow;

    @ViewById(R.id.backArrow)
    protected ImageView backArrow;

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
        placeDatabase.latleletAzonositoDAO().getAllSchool().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Place>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Place> places) {

                        schoolList = new ArrayList<>(places);

                        initView();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    @UiThread
    protected void initMap() {

        if(mapView != null){
            mapView.onResume();

            mapView.setTileSource(TileSourceFactory.MAPNIK);
            mapView.setMultiTouchControls(true);


            //set the map to Budapest
            mapView.getController().setCenter(new GeoPoint( 47.51592011212468, 19.044502645771374));
            mapView.getController().setZoom(12.0);
            mapView.invalidate();

        }
    }

    @UiThread
    protected void initView() {

        //Set a last school a first in "timeline"
        Collections.reverse(schoolList);
        adapter = new PlaceRecyclerAdapter(schoolList,getContext(),this);
        viewPager2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        viewPager2.setAdapter(adapter);


      
    }


    @Override
    public void onPlaceClick(int position) {

        mapView.getOverlays().clear();
        mapView.invalidate();

        slectedPlace = schoolList.get(position);
        GeoPoint school = new GeoPoint(Double.parseDouble(slectedPlace.getLatitude()), Double.parseDouble(slectedPlace.getLongitude()));

        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(school);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //do nothing when click on marker
        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                return false;
            }
        });
        mapView.getOverlays().add(startMarker);
        mapView.getController().setCenter(school);
        mapView.getController().setZoom(17.0);
        mapView.invalidate();

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

}


