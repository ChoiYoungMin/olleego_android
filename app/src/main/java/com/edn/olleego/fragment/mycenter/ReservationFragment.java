package com.edn.olleego.fragment.mycenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceCenterActivity;
import com.edn.olleego.adapter.mycenter.ReservationMainAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.mycenter.RecommendedFitnessModel;
import com.edn.olleego.model.mycenter.ReservationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016-08-21.
 */
public class ReservationFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private ReservationMainAdapter adapter;
    private List<ReservationModel.Result> list;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view_layout)
    RelativeLayout emptyView;
    @BindView(R.id.center_shortcut)
    RelativeLayout center_shortcut;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.title2)
    TextView title2;
    @BindView(R.id.money2)
    TextView money2;
    @BindView(R.id.location2)
    TextView location2;

    public ReservationFragment() {
    }

    public static ReservationFragment newInstance() {
        final ReservationFragment f = new ReservationFragment();
        final Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        res = mContext.getResources();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);
        ButterKnife.bind(this, v);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ReservationMainAdapter(mContext);
        recyclerView.setAdapter(adapter);

        String token = "Olleego " + Util.getPreferencesString(mContext, "login_token");

        RestfulAdapter.setOlleegoInterface(null);
        Call<ReservationModel> call = RestfulAdapter.getInstance().getReservation(token);
        call.enqueue(new Callback<ReservationModel>() {
            @Override
            public void onResponse(Call<ReservationModel> call, final retrofit2.Response<ReservationModel> response) {
                boolean isSuccessful = response.isSuccessful();
                Log.d(TAG, "isSuccessful : " + isSuccessful);
                int size = 0;
                if (isSuccessful) {
                    list = response.body().getResult();
                    list.add(list.size(), null);
                    size = list.size();
                    if (list.size() > 1)
                        adapter.setData(list);
                }
                updateView(size);
            }

            @Override
            public void onFailure(Call<ReservationModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
        return v;
    }

    void updateView(int size) {
        if (size > 1) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);

            //emptyView가 보일 때 추천 피트니스 상품 가져오기
            String token = "Olleego " + Util.getPreferencesString(mContext, "login_token");
            RestfulAdapter.setOlleegoInterface(null);
            Call<RecommendedFitnessModel> call = RestfulAdapter.getInstance().getRecommend(token);
            call.enqueue(new Callback<RecommendedFitnessModel>() {
                @Override
                public void onResponse(Call<RecommendedFitnessModel> call, final retrofit2.Response<RecommendedFitnessModel> response) {
                    boolean isSuccessful = response.isSuccessful();
                    Log.d(TAG, "isSuccessful : " + isSuccessful);
                    if (isSuccessful) {
                        List<RecommendedFitnessModel.Result> list = response.body().getResult();
                        if(list != null && list.size() > 0){
                            title.setText(list.get(0).getTitle());
                            money.setText(Util.getCurrency(list.get(0).getMoney()));
                            location.setText(list.get(0).getCenter().getCenter_binfo().getLocate());

                            title2.setText(list.get(1).getTitle());
                            money2.setText(Util.getCurrency(list.get(1).getMoney()));
                            location2.setText(list.get(1).getCenter().getCenter_binfo().getLocate());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RecommendedFitnessModel> call, Throwable t) {
                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                }
            });
        }
    }

    @OnClick({R.id.center_shortcut})
    public void onClickItem(View v){ //emptyview가 보일 때 제휴센터 바로가기 버튼
        Intent intent = new Intent(mContext, AllianceCenterActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
