package com.edn.olleego.fragment.Mission;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.mission.MissionDetailActivity;
import com.edn.olleego.adapter.mission.Mission_Adapter;
import com.edn.olleego.common.ServerInfo;
import com.edn.olleego.model.AllMissionModel;
import com.edn.olleego.server.MissionAPI;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

    RadioGroup report_input_group;

    RadioButton mission_all_sort_avg;

    RadioButton mission_all_sort_user;

    AllMissionModel allMissionModel;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_mission_category, container, false);

        ButterKnife.bind(this, rootView);

        report_input_group = (RadioGroup) rootView.findViewById(R.id.mission_all_sort_group);
        mission_all_sort_avg = (RadioButton) rootView.findViewById(R.id.mission_all_sort_avg);
        mission_all_sort_avg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    list_init("avg", inflater);
                }
            }
        });
        mission_all_sort_user = (RadioButton) rootView.findViewById(R.id.mission_all_sort_user);
        mission_all_sort_user.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    list_init("user", inflater);
                }
            }
        });
        mListView = (ListView)rootView.findViewById(R.id.mission_list);



        //missionAdapter.addItem(R.drawable.kakao_default_profile_image,getString(R.string.section_format, getArguments().getInt("position")), "복근 , 하체 , 상체");

        list_init("user" , inflater);


        now = R.id.mission_all_layout;

        // 프래그먼트 전송으로 아예 데이터를 넘기자

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MissionDetailActivity.class);
                intent.putExtra("mission_id", allMissionModel.getResult().get(position).get_id());
                //intent.putExtra("mission_type_img", rootView.findViewById(R.id.imageView27).getResources().toString()); // 이미지 전달은 어캐하지
                intent.putExtra("mission_type_name", allMissionModel.getResult().get(position).getMi_lg_sort().getValue());
                intent.putExtra("mission_title", allMissionModel.getResult().get(position).getTitle());
                intent.putExtra("mission_level", allMissionModel.getResult().get(position).getMi_level().getValue());
                intent.putExtra("mission_day", String.valueOf(allMissionModel.getResult().get(position).getMi_term()));
                intent.putExtra("mission_description", allMissionModel.getResult().get(position).getDescription1());
                intent.putExtra("mission_description2", allMissionModel.getResult().get(position).getDescription2());
                intent.putExtra("mission_img_size", allMissionModel.getResult().get(position).getDescription_img().size());
                intent.putExtra("mission_title_img", allMissionModel.getResult().get(position).getTitle_img());
                intent.putExtra("mission_type", 1);
                intent.putExtra("mission_rating", allMissionModel.getResult().get(position).getPoint_avg());
                intent.putExtra("mission_rating_people", allMissionModel.getResult().get(position).getPoint_user());
                intent.putExtra("mission_people", allMissionModel.getResult().get(position).getUser_count());

                for(int i= 0;i<allMissionModel.getResult().get(position).getDescription_img().size(); i++) {
                    intent.putExtra("mission_img"+i, allMissionModel.getResult().get(position).getDescription_img().get(i).toString());
                }


                startActivity(intent);
            }
        });




        // Inflate the layout for this fragment
        return rootView;
    }



    public void list_init(String type, LayoutInflater inflater) {
        missionAdapter = new Mission_Adapter(inflater, getContext());

        mListView.setAdapter(missionAdapter);

        missionAdapter.notifyDataSetChanged();
        missionAdapter.ItemRemove();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = configureClient(new OkHttpClient().newBuilder()) //인증서 무시
                .connectTimeout(15, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(15, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(15, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(ServerInfo.OLLEEGO_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences olleego_SP = getActivity().getSharedPreferences("olleego", getActivity().MODE_PRIVATE);
        MissionAPI allMissionAPI = retrofit.create(MissionAPI.class);
        String token = "olleego " + olleego_SP.getString("login_token", "");
        Call<AllMissionModel> lifePos = null;

        if(type.equals("user")) {
            lifePos = allMissionAPI.listRepos(token, "", "user_count");
        } else if(type.equals("avg")){
            lifePos = allMissionAPI.listRepos(token, "", "point_avg");

        }
        lifePos.enqueue(new Callback<AllMissionModel>() {
            @Override
            public void onResponse(Call<AllMissionModel> call, Response<AllMissionModel> response) {
                allMissionModel = response.body();


                if (response.isSuccessful()) {

                    for (int i = 0; i < response.body().getResult().size(); i++) {
                        try {
                            missionAdapter.addItem(response.body().getResult().get(i).getTitle_img(), response.body().getResult().get(i).getMi_lg_sort().getValue(), response.body().getResult().get(i).getTitle(), response.body().getResult().get(i).getPoint_avg(), response.body().getResult().get(i).getPoint_user(), response.body().getResult().get(i).getMi_level().getValue(), response.body().getResult().get(i).getMi_term() + "주", response.body().getResult().get(i).getUser_count());
                        } catch (NullPointerException e) {

                        }
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
    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }
        }};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return builder;
    }


}
