package matko.cv.dbhelper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;
import matko.cv.model.Place;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */

@Dao
public interface PlaceDAO {


    @Query("SELECT * FROM place")
    List<Place> getAllPlace();

    @Query("SELECT * FROM place WHERE isSchool = 1 ")
    Single<List<Place>> getAllSchool();

    @Query("SELECT * FROM place WHERE isSchool = 0 ")
    Single<List<Place>> getAllJob();


    @Insert
    void insertPlace(Place place);

    @Update
    void updatePlace(Place place);

    @Delete
    void deletePlace(Place place);
}
