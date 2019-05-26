package za.co.makuru.makurufx.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;


@Entity(nameInDb = "Currencies")
public class Currency {

    @Expose
    @SerializedName("id")
    @Property(nameInDb = "id")
    @Id
    private Long id;

    @Expose
    @SerializedName("created_at")
    @Property(nameInDb = "created_at")
    private String createdAt;

    @Expose
    @SerializedName("currency_code")
    @Property(nameInDb = "currency_code")
    private String currencyCode;

    @Expose
    @SerializedName("currency_name")
    @Property(nameInDb = "currency_name")
    private String currencyName;

    @Expose
    @SerializedName("current_rate")
    @Property(nameInDb = "current_rate")
    private String currentRate;

    @Expose
    @SerializedName("updated_at")
    @Property(nameInDb = "updated_at")
    private String updatedAt;

    @ToMany(referencedJoinProperty = "persistId")
    private List<PersistRate> persistRateList;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1033120508)
    private transient CurrencyDao myDao;

    @Generated(hash = 1505170872)
    public Currency(Long id, String createdAt, String currencyCode,
            String currencyName, String currentRate, String updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currentRate = currentRate;
        this.updatedAt = updatedAt;
    }

    @Generated(hash = 1387171739)
    public Currency() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return
                getCurrencyCode().equals(currency.getCurrencyCode());
    }

    @Override
    public int hashCode() {
        return  getCurrencyCode().hashCode();
    }

    public String getCurrentRate() {
        return this.currentRate;
    }

    public void setCurrentRate(String currentRate) {
        this.currentRate = currentRate;
    }
   

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1181619605)
    public List<PersistRate> getPersistRateList() {
        if (persistRateList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PersistRateDao targetDao = daoSession.getPersistRateDao();
            List<PersistRate> persistRateListNew = targetDao
                    ._queryCurrency_PersistRateList(id);
            synchronized (this) {
                if (persistRateList == null) {
                    persistRateList = persistRateListNew;
                }
            }
        }
        return persistRateList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1174249335)
    public synchronized void resetPersistRateList() {
        persistRateList = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 869658167)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCurrencyDao() : null;
    }
}
