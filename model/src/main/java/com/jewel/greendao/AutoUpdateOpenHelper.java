package com.jewel.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jewel.TAG;
import com.jewel.greendao.auto.DaoMaster;
import com.jewel.model.cookery.RecipeDetail;
import com.jewel.model.cookery.RecipeInfo;

import org.greenrobot.greendao.database.Database;


/**
 * 自动更新表结构的数据库OpenHelper
 * @author 王绍辉
 * @since 2017/12/25
 */

public class AutoUpdateOpenHelper extends DaoMaster.OpenHelper {

    public AutoUpdateOpenHelper(Context context, String name) {
        super(context, name);
    }

    public AutoUpdateOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.d(TAG.SQL, String.format("AutoUpdateOpenHelper:数据库升级，老版本号为:%d,新版本号为:%d", oldVersion, newVersion));
            Class[] classes = new Class[10];
            classes[0] = RecipeInfo.class;
            classes[1] = RecipeDetail.class;
            MigrationHelper.getInstance().migrate(db,classes);
        }
    }


}
