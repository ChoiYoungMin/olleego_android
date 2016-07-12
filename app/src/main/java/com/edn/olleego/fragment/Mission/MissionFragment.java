package com.edn.olleego.fragment.Mission;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_Adapter;

import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionFragment extends Fragment {

    private Mission_Adapter missionAdapter = null;

    private Fragment fragment;

    public MissionFragment() {
        // Required empty public constructor
    }

    public static MissionFragment newInstance(String param1, String param2) {
        MissionFragment fragment = new MissionFragment();

        return fragment;
    }


    ListView mListView;
    int now;
    View rootView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mission, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.toolbar_mission_catagory :
                String message = "menu_one is selected";
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_mission, container, false);

        ButterKnife.bind(this, rootView);
        actionbar_init();
        mListView = (ListView)rootView.findViewById(R.id.mission_list);

        missionAdapter = new Mission_Adapter(inflater);

        mListView.setAdapter(missionAdapter);

        missionAdapter.addItem(R.drawable.kakao_default_profile_image,"쉽게 따라하는 초보용 미션 프로그램", "복근 , 하체 , 상체");

        missionAdapter.addItem(R.drawable.kakao_default_profile_image,"쉽게 따라하는 중수용 미션 프로그램", "복근 , 하체 , 상체");
        missionAdapter.addItem(R.drawable.kakao_default_profile_image,"쉽게 따라하는 고수용 미션 프로그램", "복근 , 하체 , 상체");

        now = R.id.mission_all_layout;

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragment = new MissionDetailFragment();
                MissionDetailFragment missionDetailFragment = new MissionDetailFragment();

                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.content_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        rootView.findViewById(R.id.mission_all_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(now == R.id.mission_all_layout) {

                }
                else {
                    rootView.findViewById(now).setBackgroundColor(Color.parseColor("#ffffff"));
                    rootView.findViewById(R.id.mission_all_layout).setBackgroundResource(R.drawable.border);
                    now = R.id.mission_all_layout;

                    missionAdapter.ItemRemove();
                    missionAdapter.addItem(R.drawable.kakao_default_profile_image,"전체 메뉴", "복근 , 하체 , 상체");
                    missionAdapter.notifyDataSetChanged();
                }
            }
        });

        rootView.findViewById(R.id.mission_diet_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(now == R.id.mission_diet_layout) {

                }
                else {
                    rootView.findViewById(now).setBackgroundColor(Color.parseColor("#ffffff"));
                    rootView.findViewById(R.id.mission_diet_layout).setBackgroundResource(R.drawable.border);
                    now = R.id.mission_diet_layout;

                    missionAdapter.ItemRemove();
                    missionAdapter.addItem(R.drawable.kakao_default_profile_image,"다이어트", "복근 , 하체 , 상체");
                    missionAdapter.notifyDataSetChanged();
                }
            }
        });

        rootView.findViewById(R.id.mission_health_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(now == R.id.mission_health_layout) {

                }
                else {
                    rootView.findViewById(now).setBackgroundColor(Color.parseColor("#ffffff"));
                    rootView.findViewById(R.id.mission_health_layout).setBackgroundResource(R.drawable.border);
                    now = R.id.mission_health_layout;

                    missionAdapter.ItemRemove();
                    missionAdapter.addItem(R.drawable.kakao_default_profile_image,"필라테스", "복근 , 하체 , 상체");
                    missionAdapter.notifyDataSetChanged();
                }
            }
        });

        rootView.findViewById(R.id.mission_pilates_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(now == R.id.mission_pilates_layout) {

                }
                else {
                    rootView.findViewById(now).setBackgroundColor(Color.parseColor("#ffffff"));
                    rootView.findViewById(R.id.mission_pilates_layout).setBackgroundResource(R.drawable.border);
                    now = R.id.mission_pilates_layout;

                    missionAdapter.ItemRemove();
                    missionAdapter.addItem(R.drawable.kakao_default_profile_image,"건강관리", "복근 , 하체 , 상체");
                    missionAdapter.notifyDataSetChanged();
                }
            }
        });





        // Inflate the layout for this fragment
        return rootView;
    }



    public void actionbar_init() {

        getActivity().findViewById(R.id.toolbar_left_menu).setVisibility(View.GONE);
        getActivity().findViewById(R.id.toolbar_right_menu).setVisibility(View.GONE);

        getActivity().findViewById(R.id.toolbar_back).setVisibility(View.VISIBLE);

    }

}
