package matko.cv.fragments;

import androidx.annotation.NonNull;
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


@EFragment(R.layout.fragment_schools)
public class SchoolsFragment extends Fragment implements PlaceRecyclerAdapter.OnPlaceListener {

    @ViewById(R.id.mapSchool)
    protected MapView mapView;


    private List<Place> schoolList = new ArrayList<>();

    @ViewById(R.id.recSchooList)
    protected RecyclerView recSchooList;

    private PlaceRecyclerAdapter adapter;

    private PlaceDatabase placeDatabase;

    private Place slectedPlace;

    @Override
    public void onStart() {
        super.onStart();

        initDatabase();

        initMap();
        

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

        adapter = new PlaceRecyclerAdapter(schoolList,getContext(),this);
        recSchooList.setAdapter(adapter);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recSchooList.setLayoutManager(layoutManager);

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
        mapView.getOverlays().add(startMarker);


        mapView.getController().setCenter(school);
        mapView.getController().setZoom(17.0);
        mapView.invalidate();

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
}

