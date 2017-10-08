package com.example.elementgame.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elementgame.R;
import com.example.elementgame.model.datatypes.ElementLevel;
import com.example.elementgame.view.adapter.ElementLevelAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class LevelOptionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_ELEMENT_LEVELS = "element_levels";

    RecyclerView recyclerView;
    GridLayoutManager layoutManager;

    public LevelOptionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LevelOptionFragment newInstance(int sectionNumber, ArrayList<ElementLevel> elementLevelsSection) {
        LevelOptionFragment fragment = new LevelOptionFragment();
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

        ElementLevelAdapter adapter = new ElementLevelAdapter(getContext(), elementLevelsSection);
        recyclerView.setAdapter(adapter);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}