package com.edn.olleego.fragment.diary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.edn.olleego.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaryFoodDetailFragment extends Fragment {


    public DiaryFoodDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diary_food_detail, container, false);
    }



}
