package ru.corru.mathtin.bookmark;

import android.provider.BaseColumns;

/**
 *  Author: Daniil [Mathtin] Shigapov
 *  Copyright (c) 2017 Mathtin <wdaniil@mail.ru>
 *  This file is released under the MIT license.
 */

public class FavoriteContract {
    private FavoriteContract() {}

    public static class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_TITLE = "text";
        public static final String COLUMN_NAME_SUBTITLE = "translate";
    }
}
