package ru.corru.mathtin.bookmark;

import android.provider.BaseColumns;

/**
 * Created by wdani on 24.04.2017.
 */

public final class HistoryContract {
    private HistoryContract() {}

    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_TITLE = "text";
        public static final String COLUMN_NAME_SUBTITLE = "translate";
    }
}
