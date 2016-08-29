package com.edn.olleego.fragment.mycenter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edn.olleego.R;
import com.edn.olleego.adapter.mycenter.ReservationDetailsListAdapter;
import com.edn.olleego.model.mycenter.ReservationDetailsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kym on 2016-08-21.
 */
public class ReservationDetailsFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private ReservationDetailsListAdapter adapter;
    private List<ReservationDetailsModel.Result.PtReserve> list;
    private int reserveId;
    private boolean isOngoingPt;
    private String phoneNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public ReservationDetailsFragment() {
    }

    public static ReservationDetailsFragment newInstance(int reserveId, List<ReservationDetailsModel.Result.PtReserve> list, boolean isOngoingPt, String phoneNumber) {
        final ReservationDetailsFragment f = new ReservationDetailsFragment();
        final Bundle args = new Bundle();
        args.putString("phoneNumber", phoneNumber);
        args.putInt("reserveId", reserveId);
        args.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        args.putBoolean("isOngoingPt", isOngoingPt);
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
        phoneNumber = getArguments() != null ? getArguments().getString("phoneNumber") : "";
        reserveId = getArguments() != null ? getArguments().getInt("reserveId") : 0;
        list = getArguments() != null ? getArguments().<ReservationDetailsModel.Result.PtReserve> getParcelableArrayList("list") : null;
        isOngoingPt = getArguments() != null && getArguments().getBoolean("isOngoingPt") ? true : false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservation_details, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ReservationDetailsListAdapter(mContext, phoneNumber);
        recyclerView.setAdapter(adapter);

        if (isOngoingPt)
            list.add(0, null);
        if (isOngoingPt && list.size() == 1) {
        } else
            adapter.setData(list);
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
