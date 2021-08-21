package matko.cv.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author Matkovics Gergely<br>
 * E-mail: <a href=
 * "mailto:gergelymatkovics82@gmail.com">gergelymatkovics82@gmail.com</a>
 */


@Entity
public class Place {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String placeName;

    private String typeHU;

    private String typeEN;

    private String timeSpent;

    private String descHU;

    private String descEN;

    private String latitude;

    private String longitude;

    private int isSchool;

    @Ignore
    private boolean expanded;

    public Place(String placeName, String typeHU, String typeEN, String timeSpent, String descHU, String descEN, String latitude, String longitude, int isSchool) {
        this.placeName = placeName;
        this.typeHU = typeHU;
        this.typeEN = typeEN;
        this.timeSpent = timeSpent;
        this.descHU = descHU;
        this.descEN = descEN;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isSchool = isSchool;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String gettypeHU() {
        return typeHU;
    }

    public void settypeHU(String typeHU) {
        this.typeHU = typeHU;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getdescHU() {
        return descHU;
    }

    public void setdescHU(String descHU) {
        this.descHU = descHU;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int isSchool() {
        return isSchool;
    }

    public void setSchool(int school) {
        isSchool = school;
    }

    public String getTypeHU() {
        return typeHU;
    }

    public void setTypeHU(String typeHU) {
        this.typeHU = typeHU;
    }

    public String getTypeEN() {
        return typeEN;
    }

    public void setTypeEN(String typeEN) {
        this.typeEN = typeEN;
    }

    public String getDescHU() {
        return descHU;
    }

    public void setDescHU(String descHU) {
        this.descHU = descHU;
    }

    public String getDescEN() {
        return descEN;
    }

    public void setDescEN(String descEN) {
        this.descEN = descEN;
    }
}
