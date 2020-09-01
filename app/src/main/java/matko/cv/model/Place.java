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

    private String type;

    private String timeSpent;

    private String desc;

    private String latitude;

    private String longitude;

    private boolean isSchool;

    @Ignore
    private boolean expanded;

    public Place(String placeName, String type, String timeSpent, String desc, String latitude, String longitude, boolean isSchool) {
        this.placeName = placeName;
        this.type = type;
        this.timeSpent = timeSpent;
        this.desc = desc;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isSchool() {
        return isSchool;
    }

    public void setSchool(boolean school) {
        isSchool = school;
    }
}
