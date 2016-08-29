package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AlliancePTListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 1..
 *
 * 제휴센터 상세 페이지 > 센터안내 ListAdapter
 */
public class AllianceCenterInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private final static int DESCRIPTION_VIEW = 77;
    private final static int PT_LIST_VIEW = 78;

    private Context mContext;
    private Resources res;
    private List<AlliancePTListModel> list = new ArrayList<>();
    private List<AllianceDetailsModel> detalisList = new ArrayList<>();
    private AllianceClassListAdapter allianceClassListAdapter;
    private AlliancePTListModel.Result result;

    public AllianceCenterInfoListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        res = mContext.getResources();
    }

    public void setData(List<AlliancePTListModel> data, List<AllianceDetailsModel> detalisList) {
        if (data != null) list = data;

        this.detalisList = detalisList;

        notifyDataSetChanged();
    }

    public class DescriptionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerview_horizontal)
        RecyclerView classRecyclerView;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.empty_view_layout)
        RelativeLayout emptyView;

        public DescriptionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            description.setText(detalisList.get(0).getResult().getCenter_dinfo().getDescription());
            allianceClassListAdapter.setData(detalisList);

            if(getItemCount() == 1){
                emptyView.setVisibility(View.VISIBLE);
            }
        }
    }

    public class AlliancePTListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.expiry)
        TextView expiry;

        public AlliancePTListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult().get(position);
            title.setText(result.getTitle());
            money.setText(Util.getCurrency(result.getMoney()));
            expiry.setText(String.format(res.getString(R.string.expiry_text), String.valueOf(result.getExpiry())));
        }

        @OnClick({R.id.cardview})
        public void onClickItem(View v) {

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if(viewType == DESCRIPTION_VIEW){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_center_info_description_item, null);
            DescriptionViewHolder viewHolder = new DescriptionViewHolder(v);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            viewHolder.classRecyclerView.setLayoutManager(layoutManager);
            allianceClassListAdapter = new AllianceClassListAdapter(mContext);
            viewHolder.classRecyclerView.setAdapter(allianceClassListAdapter);
            viewHolder.classRecyclerView.setNestedScrollingEnabled(false);
            return viewHolder;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_pt_list_item, null);
        AlliancePTListHolder alliancePTListHolder = new AlliancePTListHolder(v);
        return alliancePTListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof DescriptionViewHolder) {
                DescriptionViewHolder vh = (DescriptionViewHolder) holder;
                vh.bindView(position);
            } else if (holder instanceof AlliancePTListHolder) {
                AlliancePTListHolder vh = (AlliancePTListHolder) holder;
                vh.bindView(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return DESCRIPTION_VIEW;
        }
        return PT_LIST_VIEW;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }
}