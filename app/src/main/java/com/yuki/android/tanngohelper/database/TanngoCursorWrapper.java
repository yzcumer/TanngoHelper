package com.yuki.android.tanngohelper.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.yuki.android.tanngohelper.Tanngo;
import com.yuki.android.tanngohelper.database.TanngoDbSchema.TanngoTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by fxf on 2016/10/27.
 */
public class TanngoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TanngoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Tanngo getTanngo() {
        String uuidString = getString(getColumnIndex(TanngoTable.Columns.UUID));
        String word = getString(getColumnIndex(TanngoTable.Columns.WORD));
        String mean = getString(getColumnIndex(TanngoTable.Columns.MEAN));
        long date = getLong(getColumnIndex(TanngoTable.Columns.DATE));
        int isSolved = getInt(getColumnIndex(TanngoTable.Columns.SOLVED));

        Tanngo tanngo = new Tanngo(UUID.fromString(uuidString));
        tanngo.setWord(word);
        tanngo.setMean(mean);
        tanngo.setDate(new Date(date));
        tanngo.setSolved(isSolved != 0);

        return tanngo;
    }
}
