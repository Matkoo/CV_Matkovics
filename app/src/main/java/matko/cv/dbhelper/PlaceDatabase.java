package matko.cv.dbhelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import matko.cv.model.Place;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */


@Database(entities = Place.class,exportSchema = false,version = 3)
public abstract class PlaceDatabase extends RoomDatabase {

    private static final String DATABASE_NAME ="place_db" ;
    private static PlaceDatabase instance;

    public static synchronized PlaceDatabase getInstance(Context context){
        if(instance ==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),PlaceDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract PlaceDAO latleletAzonositoDAO();
}
