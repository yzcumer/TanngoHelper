package com.yuki.android.tanngohelper.database;

/**
 * Created by fxf on 2016/11/7.
 */
public class TanngoDbSchema {
    public static final class TanngoTable {
        public static final String NAME = "tanngos";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String WORD = "word";
            public static final String MEAN = "mean";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
