package com.edn.olleego.fragment.mycenter;

import android.content.Context;
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

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.edn.olleego.R;
import com.edn.olleego.adapter.mycenter.ConsultDetailsListAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.mycenter.ConsultChildModel;
import com.edn.olleego.model.mycenter.ConsultDetailsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016-08-21.
 */
public class ConsultDetailsFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private ConsultDetailsListAdapter adapter;
    private int reserveId;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public ConsultDetailsFragment() {
    }

    public static ConsultDetailsFragment newInstance(int reserveId) {
        final ConsultDetailsFragment f = new ConsultDetailsFragment();
        final Bundle args = new Bundle();
        args.putInt("reserveId", reserveId);
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
        reserveId = getArguments() != null ? getArguments().getInt("reserveId") : 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation_details, container, false);
        ButterKnife.bind(this, v);
        String token = "Olleego " + Util.getPreferencesString(mContext, "login_token");

        RestfulAdapter.setOlleegoInterface(null);
        Call<ConsultDetailsModel> call = RestfulAdapter.getInstance().getConsultDetails(token, reserveId, "consult");
        call.enqueue(new Callback<ConsultDetailsModel>() {
            @Override
            public void onResponse(Call<ConsultDetailsModel> call, final retrofit2.Response<ConsultDetailsModel> response) {
                boolean isSuccessful = response.isSuccessful();
                Log.d(TAG, "isSuccessful : " + isSuccessful);
                if (isSuccessful) {
                    List<ConsultDetailsModel.Result.PtReserve> list = response.body().getResult().getPt_reserve();

                    int cnt = 0;
                    for (ConsultDetailsModel.Result.PtReserve info : list) {
                        List<ConsultChildModel> childList = new ArrayList<>();
                        ConsultChildModel model = new ConsultChildModel();
                        if (info.getIn_body() != null) {
                            model.set_id(info.getIn_body().get_id());
                            model.setBasal_metabolism(info.getIn_body().getBasal_metabolism());
                            model.setBmi(info.getIn_body().getBmi());
                            model.setBody_fat(info.getIn_body().getBody_fat());
                            model.setBody_fat_per(info.getIn_body().getBody_fat_per());
                            model.setMuscle(info.getIn_body().getMuscle());
                            model.setWeight(info.getIn_body().getWeight());
                            model.setWhr(info.getIn_body().getWhr());
                        }
                        if (info.getConsult_text() != null && !info.getConsult_text().equals("null"))
                            model.setConsult_text(info.getConsult_text());

                        childList.add(model);
                        list.get(cnt).setChildItemList(childList);
                        cnt++;
                    }

                    adapter = new ConsultDetailsListAdapter(mContext, list);
                    adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                        @Override
                        public void onListItemExpanded(int position) {
                        }
                        @Override
                        public void onListItemCollapsed(int position) {
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ConsultDetailsModel> call, Throwable t) {
                Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
            }
        });
        return v;
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
