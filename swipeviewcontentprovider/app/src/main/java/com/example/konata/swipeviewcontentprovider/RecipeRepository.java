package com.example.konata.swipeviewcontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by marmm on 15/03/2018.
 */

public class RecipeRepository {

    private SQLiteDatabase database;
    private final DBHelper dbHelper;
    private final String[] RECIPES_ALL_COLUMNS = {
            RecipeContract.RecipeEntry._ID,
            RecipeContract.RecipeEntry.COLUMN_NAME_NAME,
            RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION,
            RecipeContract.RecipeEntry.COLUMN_IMAGE};

    private Context context;

    public RecipeRepository(Context context) {
        dbHelper = new DBHelper(context);

        this.context = context;
    }

    public int count() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor mCount = db.rawQuery("select count(*) from " + RecipeContract.RecipeEntry.TABLE_NAME, null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();

        return count;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);return outputStream.toByteArray();
    }

    public void save(Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());
        values.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, getBitmapAsByteArray(recipe.getImage()));

        // Inserting Row
        db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void update(int id, Recipe recipe) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_NAME, recipe.getName());
        values.put(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION, recipe.getDescription());
        values.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, getBitmapAsByteArray(recipe.getImage()));

        db.update(RecipeContract.RecipeEntry.TABLE_NAME, values, RecipeContract.RecipeEntry._ID + "= ?", new String[]{String.valueOf(id)});
        db.close(); // Closing database connection
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(RecipeContract.RecipeEntry.TABLE_NAME, RecipeContract.RecipeEntry._ID + " =?",
                new String[]{Integer.toString(id)});
        db.close();

    }

    public Cursor findAll() {
        // If we have not yet opened the database, open it
        if (database == null) {
            database = dbHelper.getReadableDatabase();
        }

        return database.query(RecipeContract.RecipeEntry.TABLE_NAME, RECIPES_ALL_COLUMNS, null, null, null, null, null);
    }

    public void reset() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME);
        dbHelper.onCreate(db);

        //Add default items.
        if (this.count() == 0)
        {
            Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.hamburger);
            this.save(new Recipe(0, context.getResources().getString(R.string.recipe_hamburger_name), context.getResources().getString(R.string.recipe_hamburger_description), image));
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.turkey);
            this.save(new Recipe(0, context.getResources().getString(R.string.recipe_turkey_name), context.getResources().getString(R.string.recipe_turkey_description), image));
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.chicken);
            this.save(new Recipe(0, context.getResources().getString(R.string.recipe_chicken_name), context.getResources().getString(R.string.recipe_chicken_description), image));
    }

        db.close();
    }

    public Recipe getItem(int i) {
        // If we have not yet opened the database, open it
        if (database == null) {
            database = dbHelper.getReadableDatabase();
        }

        Cursor c = database.query(RecipeContract.RecipeEntry.TABLE_NAME, RECIPES_ALL_COLUMNS, null, null, null, null, null);
        c.move(i);

        String name = c.getString(c.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_NAME));
        String description = c.getString(c.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME_DESCRIPTION));

        byte[] imgByte = c.getBlob(c.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_IMAGE));
        Bitmap b = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);

        return new Recipe(0, name, description, b );
    }
}
