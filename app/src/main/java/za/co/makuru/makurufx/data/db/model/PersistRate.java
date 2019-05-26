package za.co.makuru.makurufx.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "persisted")
public class PersistRate {

    @Expose
    @SerializedName("id")
    @Id(autoincrement = true)
    private Long id;

    @Expose
    @SerializedName("timestamp")
    @Property(nameInDb = "timestamp")
    private Integer timestamp;

    @Expose
    @SerializedName("then_rate")
    @Property(nameInDb = "then_rate")
    private String thenRate;

    @Expose
    @SerializedName("persist_id")
    @Property(nameInDb = "persist_id")
    private Long persistId;

    public PersistRate(String thenRate, Integer timestamp) {
        this.thenRate = thenRate;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1530806215)
    public PersistRate(Long id, Integer timestamp, String thenRate,
            Long persistId) {
        this.id = id;
        this.timestamp = timestamp;
        this.thenRate = thenRate;
        this.persistId = persistId;
    }

    @Generated(hash = 144620169)
    public PersistRate() {
    }

    public Integer getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getThenRate() {
        return this.thenRate;
    }

    public void setThenRate(String thenRate) {
        this.thenRate = thenRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersistId() {
        return persistId;
    }

    public void setPersistId(Long persistId) {
        this.persistId = persistId;
    }
}
