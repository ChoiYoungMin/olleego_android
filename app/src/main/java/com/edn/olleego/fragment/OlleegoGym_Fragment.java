package com.edn.olleego.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.OlleegoGymActivity;
import com.edn.olleego.activity.gym.GymDetailActivity;
import com.edn.olleego.adapter.olleegogym.OlleegGym_Adapter;

public class OlleegoGym_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public OlleegoGym_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OlleegoGym_Fragment newInstance(String param1, String param2) {
        OlleegoGym_Fragment fragment = new OlleegoGym_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_olleego_gym_, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        ListView listView = (ListView)rootView.findViewById(R.id.gymlist);
        OlleegGym_Adapter olleegGym_adapter = new OlleegGym_Adapter(inflater);

        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","대방동 피트니스","100,000 원","50,000 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","새마을 피트니스","200,000 원","12,245 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","대방동 피트니스","100,000 원","50,000 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","새마을 피트니스","200,000 원","12,245 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","대방동 피트니스","100,000 원","50,000 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","새마을 피트니스","200,000 원","12,245 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","대방동 피트니스","100,000 원","50,000 원");
        olleegGym_adapter.add(R.drawable.kakao_default_profile_image, "피트니스","새마을 피트니스","200,000 원","12,245 원");

        listView.setAdapter(olleegGym_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GymDetailActivity.class);
                startActivity(intent);

            }
        });



        return rootView;

    }




}
