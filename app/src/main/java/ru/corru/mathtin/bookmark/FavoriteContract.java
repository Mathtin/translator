package ru.corru.mathtin.bookmark;

import android.provider.BaseColumns;

/**
 * Created by wdani on 24.04.2017.
 */

public class FavoriteContract {
    private FavoriteContract() {}

    public static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_TITLE = "text";
        public static final String COLUMN_NAME_SUBTITLE = "translate";
    }
}
