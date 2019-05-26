package za.co.makuru.makurufx.data.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import javax.inject.Inject;

import za.co.makuru.makurufx.data.db.model.DaoMaster;
import za.co.makuru.makurufx.di.ApplicationContext;
import za.co.makuru.makurufx.di.DatabaseInfo;

public class DatabaseOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DatabaseOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
        }
    }
}
