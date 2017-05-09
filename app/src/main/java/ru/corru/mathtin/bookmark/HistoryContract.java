package ru.corru.mathtin.bookmark;

import android.provider.BaseColumns;

/**
 *  Author: Daniil [Mathtin] Shigapov
 *  Copyright (c) 2017 Mathtin <wdaniil@mail.ru>
 *  This file is released under the MIT license.
 */

public final class HistoryContract {
    private HistoryContract() {}

    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_TITLE = "text";
        public static final String COLUMN_NAME_SUBTITLE = "translate";
    }
}
