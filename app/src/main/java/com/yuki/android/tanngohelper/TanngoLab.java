package com.yuki.android.tanngohelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuki.android.tanngohelper.database.TanngoBaseHelper;
import com.yuki.android.tanngohelper.database.TanngoCursorWrapper;
import com.yuki.android.tanngohelper.database.TanngoDbSchema.TanngoTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by fxf on 2016/10/25.
 * implement CrimeLab as a singleton
 */

public class TanngoLab {
    private static TanngoLab sTanngoLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;
    //private ArrayList<Tanngo> mTanngos;

    public static TanngoLab get(Context context) {
        if (sTanngoLab == null) {
            sTanngoLab = new TanngoLab(context);
        }
        return sTanngoLab;
    }

    private TanngoLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TanngoBaseHelper(mContext).getWritableDatabase();
        /*mTanngos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Tanngo tanngo = new Tanngo();
            tanngo.setWord("Crime #" + i);
            mTanngos.add(tanngo);
        }*/
    }

    private static ContentValues getContentValues(Tanngo tanngo) {
        ContentValues values = new ContentValues();
        values.put(TanngoTable.Columns.UUID, tanngo.getId().toString());
        values.put(TanngoTable.Columns.WORD, tanngo.getWord());
        values.put(TanngoTable.Columns.MEAN, tanngo.getMean());
        values.put(TanngoTable.Columns.DATE, tanngo.getDate().getTime());
        values.put(TanngoTable.Columns.SOLVED, tanngo.isSolved() ? 1 : 0);

        return values;
    }

    public void addTanngo(Tanngo t) {
        ContentValues values = getContentValues(t);
        mDatabase.insert(TanngoTable.NAME, null, values);
        //mTanngos.add(t);
    }

    public void updateTanngo(Tanngo tanngo) {
        String uuidString = tanngo.getId().toString();
        ContentValues values = getContentValues(tanngo);

        mDatabase.update(TanngoTable.NAME, values,
                TanngoTable.Columns.UUID + "=?",
                new String[]{uuidString});
    }

    private TanngoCursorWrapper queryTanngos(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TanngoTable.NAME,
                null,// Columns - null selects all columns
                whereClause,
                whereArgs,
                null,// groupBy
                null,// having
                null// orderBy
        );
        return new TanngoCursorWrapper(cursor);
    }

    //List<E> is an interface that supports an ordered list
    // of objects of a given type. It defines methods for retrieving,
    // adding, and deleting elements. A commonly used implementation of List is
    // ArrayList, which uses a regular Java array to store the list elements.
    public List<Tanngo> getTanngos() {
        List<Tanngo> tanngos = new ArrayList<>();
        TanngoCursorWrapper cursor = queryTanngos(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tanngos.add(cursor.getTanngo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tanngos;
        //return new ArrayList<>();
    }

    public Tanngo getTanngo(UUID id) {
        /*for (Tanngo tanngo:mTanngos){
            if (tanngo.getId().equals(id)){
                return tanngo;
            }
        }*/
        TanngoCursorWrapper cursor = queryTanngos(
                TanngoTable.Columns.UUID + "= ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTanngo();
        } finally {
            cursor.close();
        }
        //return null;
    }
}
