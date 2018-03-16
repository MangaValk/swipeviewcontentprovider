package com.example.konata.swipeviewcontentprovider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private RecipeRepository recipeRepository;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            this.recipeRepository = new RecipeRepository(getApplicationContext());
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a RecipeFragment (defined as a static inner class below).
            position++;
            Recipe r = recipeRepository.getItem(position);
            return RecipeFragment.newInstance( r.getName(), r.getDescription(), r.getImage() );
        }

        @Override
        public int getCount() {
            return this.recipeRepository.count();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RecipeFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_RECIPE_TITLE = "ARG_RECIPE_TITLE";
        private static final String ARG_RECIPE_INSTRUCTION = "ARG_RECIPE_INSTRUCTION";
        private static final String ARG_RECIPE_IMAGE = "ARG_RECIPE_IMAGE";

        public RecipeFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RecipeFragment newInstance(String recipeName, String recipeInstruction, Bitmap b) {
            RecipeFragment fragment = new RecipeFragment();
            Bundle args = new Bundle();

            args.putString(ARG_RECIPE_TITLE, recipeName);
            args.putString(ARG_RECIPE_INSTRUCTION, recipeInstruction);
            args.putByteArray(ARG_RECIPE_IMAGE, getBitmapAsByteArray(b));

            fragment.setArguments(args);
            return fragment;
        }

        public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
            return outputStream.toByteArray();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

            TextView textView = rootView.findViewById(R.id.recipe_name);
            TextView textView2 = rootView.findViewById(R.id.recipe_instruction);
            ImageView imageView = rootView.findViewById(R.id.recipe_image);

            textView.setText( getArguments().getString(ARG_RECIPE_TITLE) );
            textView2.setText( getArguments().getString(ARG_RECIPE_INSTRUCTION) );
            textView2.setText( getArguments().getString(ARG_RECIPE_INSTRUCTION) );


            byte[] imgByte = getArguments().getByteArray(ARG_RECIPE_IMAGE);
            Bitmap b = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            imageView.setImageBitmap( b );

            return rootView;
        }
    }
}
