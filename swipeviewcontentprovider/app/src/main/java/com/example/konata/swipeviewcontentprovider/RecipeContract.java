package com.example.konata.swipeviewcontentprovider;

import android.provider.BaseColumns;

/**
 * Created by marmm on 15/03/2018.
 */

public final class RecipeContract {

    private RecipeContract() {
    }

   /* Inner class that defines the table contents */

    public static class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Recipes";
        // Labels Table Columns names
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE = "image";

    }

}
