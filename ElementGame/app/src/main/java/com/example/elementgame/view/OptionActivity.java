package com.example.elementgame.view;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.elementgame.R;
import com.example.elementgame.model.datatypes.ElementLevel;
import com.example.elementgame.model.types.TaskType;
import com.example.elementgame.view.adapters.MyRecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionActivity extends ElementActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @BindView(R.id.container) ViewPager mViewPager;
    @BindView(R.id.previous_button)FloatingActionButton previousButton;
    @BindView(R.id.next_button)FloatingActionButton nextButton;
    private int currentPage;
    private ArrayList<ElementLevel> elementLevels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ButterKnife.bind(this);

        initActivity();
    }

    @Override
    protected void initActivity() {
        elementLevels = (ArrayList<ElementLevel>) getIntent().getSerializableExtra("Levels");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), elementLevels);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        currentPage = mViewPager.getCurrentItem();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage = getItem(+1);
                mViewPager.setCurrentItem(currentPage, true);
                checkButtons();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage = getItem(-1);
                mViewPager.setCurrentItem(currentPage, true);
                checkButtons();
            }
        });

        checkButtons();
    }

    @Override
    public void UpdateOnTaskFinished(TaskType taskType, Object data) {
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    private void checkButtons(){
        if(currentPage >= mSectionsPagerAdapter.getCount() -1){
            nextButton.setVisibility(View.INVISIBLE);
        }
        else {
            nextButton.setVisibility(View.VISIBLE);
        }

        if(currentPage == 0){
            previousButton.setVisibility(View.INVISIBLE);
        }
        else {
            previousButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_ELEMENT_LEVELS = "element_levels";
        private String[] nameList = {"Air test", "Fire test", "Earth test", "Water test"};
        private int[] iconList = {R.drawable.air_element, R.drawable.fire_element, R.drawable.earth_element, R.drawable.water_element};

        RecyclerView recyclerView;
        GridLayoutManager layoutManager;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, ArrayList<ElementLevel> elementLevelsSection) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putSerializable(ARG_ELEMENT_LEVELS, elementLevelsSection);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_option, container, false);

            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            layoutManager = new GridLayoutManager(getContext(), 2);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);


            ArrayList<ElementLevel> elementLevelsSection = (ArrayList<ElementLevel>)getArguments().getSerializable(ARG_ELEMENT_LEVELS);
            if(elementLevelsSection == null){
                elementLevelsSection = new ArrayList<>();
            }

            nameList = new String[elementLevelsSection.size()];
            iconList = new int[elementLevelsSection.size()];
            for(int i = 0; i < elementLevelsSection.size(); i++){
                nameList[i] = elementLevelsSection.get(i).getName();
                iconList[i] = getResources().getIdentifier(elementLevelsSection.get(i).getIconID(), "drawable", getContext().getPackageName());
            }

            MyRecyclerAdapter adapter = new MyRecyclerAdapter(getContext(), iconList, nameList);
            recyclerView.setAdapter(adapter);

            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<ElementLevel> elementLevels;
        private int viewsPerPage = 16;
        public SectionsPagerAdapter(FragmentManager fm, ArrayList<ElementLevel> elementLevels) {
            super(fm);
            this.elementLevels = elementLevels;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            ArrayList<ElementLevel> elementLevelsSection = new ArrayList<>();

            int loopUntil = viewsPerPage * (position + 1);
            if(elementLevels.size() < viewsPerPage) {
                loopUntil = elementLevels.size();
            }

            for (int i = viewsPerPage * position; i < loopUntil; i++)
                elementLevelsSection.add(elementLevels.get(i));

            return PlaceholderFragment.newInstance(position + 1, elementLevelsSection);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return (int) Math.ceil((double)elementLevels.size() / viewsPerPage);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position < getCount()) {
                return String.format("SECTION %d", position + 1);
            }
            return null;
        }
    }
}
