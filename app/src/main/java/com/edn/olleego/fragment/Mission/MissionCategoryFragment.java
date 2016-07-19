package com.edn.olleego.fragment.Mission;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.adapter.mission.Mission_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.server.AllMissionAPI;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionCategoryFragment extends Fragment {

    private Mission_Adapter missionAdapter = null;

    private Fragment fragment;

    public MissionCategoryFragment() {
        // Required empty public constructor
    }

    public static MissionCategoryFragment newInstance(int position) {
        MissionCategoryFragment fragment = new MissionCategoryFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
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


        rootView = inflater.inflate(R.layout.fragment_mission_category, container, false);

        ButterKnife.bind(this, rootView);

        mListView = (ListView)rootView.findViewById(R.id.mission_list);

        missionAdapter = new Mission_Adapter(inflater, getContext());

        mListView.setAdapter(missionAdapter);

        //missionAdapter.addItem(R.drawable.kakao_default_profile_image,getString(R.string.section_format, getArguments().getInt("position")), "복근 , 하체 , 상체");

        list_init();


        now = R.id.mission_all_layout;

        // 프래그먼트 전송으로 아예 데이터를 넘기자

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragment = new MissionDetailFragment();
                MissionDetailFragment missionDetailFragment = new MissionDetailFragment();

                android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.add(R.id.content_main, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });




        // Inflate the layout for this fragment
        return rootView;
    }



    public void list_init() {
        missionAdapter.notifyDataSetChanged();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);
        AllMissionAPI allMissionAPI = retrofit.create(AllMissionAPI.class);
        String token = "ollego " + olleego_SP.getString("login_token", "");
        final Call<AllMissionModel> lifePos = allMissionAPI.listRepos(token);

        lifePos.enqueue(new Callback<AllMissionModel>() {
            @Override
            public void onResponse(Call<AllMissionModel> call, Response<AllMissionModel> response) {

                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        missionAdapter.addItem(ServerInfo.OLLEEGO_IMAGE+response.body().getResult().get(i).getTitle_img(), response.body().getResult().get(i).getMi_lg_sort().getValue(), response.body().getResult().get(i).getTitle(), 3, 00, response.body().getResult().get(i).getMi_level().getValue(), response.body().getResult().get(i).getMi_term() + "주");

                    }
                }
                else  {
                    Toast.makeText(getContext(),"미션 데이터가 없습니당 ㅎㅎ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllMissionModel> call, Throwable t) {

            }
        });
    }


}