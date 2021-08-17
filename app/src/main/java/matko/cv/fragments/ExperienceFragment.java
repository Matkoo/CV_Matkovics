package matko.cv.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
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

    @ViewById(R.id.mapExp)
    protected MapView mapView;

    private List<Place> expList = new ArrayList<>();

    @ViewById(R.id.recExpList)
    protected RecyclerView recExpList;

    private PlaceRecyclerAdapter adapter;

    private PlaceDatabase placeDatabase;

    private Place slectedPlace;

    @Override
    public void onStart() {
        super.onStart();

        initDatabase();
        intiView();
        initMap();

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
                        if(mapView != null)
                            mapView.invalidate();
                        intiView();

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


            //set the map to Tatabánya
            mapView.getController().setCenter(new GeoPoint( 47.560504585701146, 18.424900931157893));
            mapView.getController().setZoom(14.0);
            mapView.invalidate();

        }
    }

    @UiThread
    protected void intiView() {

        adapter = new PlaceRecyclerAdapter(expList, getContext(), this);
        if(adapter != null ) {
            recExpList.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recExpList.setLayoutManager(layoutManager);
        }else{
            Log.e(TAG, "Unsuccesful load the adapter" );
        }
        
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.mapExp);

        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    public void onPlaceClick(int position) {

        mapView.getOverlays().clear();
        mapView.invalidate();

        slectedPlace = expList.get(position);
        GeoPoint job = new GeoPoint(Double.parseDouble(slectedPlace.getLatitude()), Double.parseDouble(slectedPlace.getLongitude()));

        Marker startMarker = new Marker(mapView);
        startMarker.setPosition(job);
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(startMarker);


        mapView.getController().setCenter(job);
        mapView.getController().setZoom(17.0);
        mapView.invalidate();

    }
}
