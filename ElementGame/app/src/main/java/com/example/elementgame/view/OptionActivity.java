package com.example.elementgame.view;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.elementgame.R;
import com.example.elementgame.model.datatypes.ElementLevel;
import com.example.elementgame.model.types.TaskType;
import com.example.elementgame.view.fragment.LevelOptionFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionActivity extends ElementActivity{

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
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        private ArrayList<ElementLevel> elementLevels;
        private int viewsPerPage = 4;
        public SectionsPagerAdapter(FragmentManager fm, ArrayList<ElementLevel> elementLevels) {
            super(fm);
            this.elementLevels = elementLevels;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a LevelOptionFragment (defined as a static inner class below).
            ArrayList<ElementLevel> elementLevelsSection = new ArrayList<>();

            int loopUntil = viewsPerPage * (position + 1);
            if(elementLevels.size() < viewsPerPage * (position + 1)) {
                loopUntil = elementLevels.size();
            }

            for (int i = viewsPerPage * position; i < loopUntil; i++)
                elementLevelsSection.add(elementLevels.get(i));

            return LevelOptionFragment.newInstance(position + 1, elementLevelsSection);
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
