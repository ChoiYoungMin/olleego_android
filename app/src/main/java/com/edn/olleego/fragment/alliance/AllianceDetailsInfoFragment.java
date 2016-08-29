package com.edn.olleego.fragment.alliance;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edn.olleego.R;
import com.edn.olleego.adapter.alliance.AllianceDetailsInfoListAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceTrainersModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 7..
 *
 * 센터 상세 페이지내 세부정보 탭
 */
public class AllianceDetailsInfoFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private List<AllianceDetailsModel> detalisList;
    private AllianceDetailsInfoListAdapter detailsInfoListAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public AllianceDetailsInfoFragment() {
    }

    public static AllianceDetailsInfoFragment newInstance(List<AllianceDetailsModel> list) {
        final AllianceDetailsInfoFragment f = new AllianceDetailsInfoFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detalisList = getArguments() != null ? getArguments().<AllianceDetailsModel>getParcelableArrayList("list") : null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alliance_details_info_list, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        detailsInfoListAdapter = new AllianceDetailsInfoListAdapter(mContext);
        recyclerView.setAdapter(detailsInfoListAdapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(detalisList.get(0).getResult() != null){
            RestfulAdapter.setOlleegoInterface(null);
            Call<AllianceTrainersModel> call = RestfulAdapter.getInstance().getTrainersList(detalisList.get(0).getResult().get_id());
            call.enqueue(new Callback<AllianceTrainersModel>() {
                @Override
                public void onResponse(Call<AllianceTrainersModel> call, retrofit2.Response<AllianceTrainersModel> response) {
                    Log.d(TAG, "isSuccessful : " + response.isSuccessful());
                    if (response.isSuccessful()) {

                        /**
                         *   //kym...
                         *   headerview, listview)
                         */
                        AllianceTrainersModel model = response.body();
                        model.getResult().add(0, null);
                        model.getResult().add(model.getResult().size(), null);
                        List<AllianceTrainersModel> trainersList = new ArrayList<>();
                        trainersList.add(model);

                        if (detailsInfoListAdapter != null)
                            detailsInfoListAdapter.setData(trainersList, detalisList);
                    }
                }

                @Override
                public void onFailure(Call<AllianceTrainersModel> call, Throwable t) {
                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
