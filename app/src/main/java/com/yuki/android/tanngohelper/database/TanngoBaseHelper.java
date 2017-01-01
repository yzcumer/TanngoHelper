package com.yuki.android.tanngohelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yuki.android.tanngohelper.database.TanngoDbSchema.TanngoTable;

/**
 * Created by fxf on 2016/11/1.
 */
/*public class TanngoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tanngoBase.db";

    public TanngoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table" + TanngoTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                TanngoTable.Columns.UUID + "," +
                TanngoTable.Columns.WORD + "," +
                TanngoTable.Columns.MEAN + "," +
                TanngoTable.Columns.DATE + "," +
                TanngoTable.Columns.SOLVED +
                ")"
        );
    }

    @Override
    public void nUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}*/
public class TanngoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tanngoBase.db";

    public TanngoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TanngoTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TanngoTable.Columns.UUID + ", " +
                TanngoTable.Columns.WORD + ", " +
                TanngoTable.Columns.MEAN + ", " +
                TanngoTable.Columns.DATE + ", " +
                TanngoTable.Columns.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

