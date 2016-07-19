package com.edn.olleego.fragment.Mission;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edn.olleego.R;
import com.edn.olleego.activity.mission.MissionCustomStepOneActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionCustomFragment extends Fragment {


    public MissionCustomFragment() {
        // Required empty public constructor
    }

    public static MissionCustomFragment newInstance(int position) {
        MissionCustomFragment fragment = new MissionCustomFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mission_custom, container, false);

        view.findViewById(R.id.mission_custom_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MissionCustomStepOneActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
