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
import com.edn.olleego.adapter.alliance.AllianceCenterInfoListAdapter;
import com.edn.olleego.adapter.alliance.AllianceClassListAdapter;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AlliancePTListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kym on 2016. 8. 7..
 *
 * 센터 상세 페이지내 센터안내 탭
 */
public class AllianceCenterInfoFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private List<AllianceDetailsModel> detalisList;
    private AllianceCenterInfoListAdapter ptListAdapter;
    private AllianceClassListAdapter classListAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    public AllianceCenterInfoFragment() {
    }

    public static AllianceCenterInfoFragment newInstance(List<AllianceDetailsModel> list) {
        final AllianceCenterInfoFragment f = new AllianceCenterInfoFragment();
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
        View v = inflater.inflate(R.layout.fragment_alliance_center_info_list, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ptListAdapter = new AllianceCenterInfoListAdapter(mContext, this);
        recyclerView.setAdapter(ptListAdapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface AllianceService {
        @GET("/api/v2/pts/center/{id}")
        Call<AlliancePTListModel> getPTList(@Path("id") int id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (detalisList != null && detalisList.size() > 0) {
            RestfulAdapter.setOlleegoInterface(null);
            Call<AlliancePTListModel> call = RestfulAdapter.getInstance().getPTList(detalisList.get(0).getResult().get_id());
            call.enqueue(new Callback<AlliancePTListModel>() {
                @Override
                public void onResponse(Call<AlliancePTListModel> call, retrofit2.Response<AlliancePTListModel> response) {
                    Log.d(TAG, "isSuccessful : " + response.isSuccessful());

                    AlliancePTListModel model = new AlliancePTListModel();
                    if(response.body() != null) model = response.body();

                    model.getResult().add(0, null);
                    List<AlliancePTListModel> ptList = new ArrayList<>();
                    ptList.add(model);
                    if (ptListAdapter != null)
                        ptListAdapter.setData(ptList, detalisList);
                }

                @Override
                public void onFailure(Call<AlliancePTListModel> call, Throwable t) {
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
