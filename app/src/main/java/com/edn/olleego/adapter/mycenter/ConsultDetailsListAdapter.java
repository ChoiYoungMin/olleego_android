package com.edn.olleego.adapter.mycenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.edn.olleego.R;
import com.edn.olleego.model.mycenter.ConsultChildModel;
import com.edn.olleego.model.mycenter.ConsultDetailsModel;

import java.util.List;

/**
 * Created by pc on 2016-08-22.
 */
public class ConsultDetailsListAdapter extends ExpandableRecyclerAdapter<ConsultParentViewHolder, ConsultChildViewHolder> {
    private Context mContext;
    private LayoutInflater mInflator;

    public ConsultDetailsListAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mContext = context;
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public ConsultParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parent = mInflator.inflate(R.layout.mycenter_consult_parent_view_item, parentViewGroup, false);
        return new ConsultParentViewHolder(parent, mContext);
    }

    @Override
    public ConsultChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View child = mInflator.inflate(R.layout.mycenter_consult_child_view_item, childViewGroup, false);
        return new ConsultChildViewHolder(child);
    }

    @Override
    public void onBindParentViewHolder(ConsultParentViewHolder consultParentViewHolder, int position, ParentListItem parentListItem) {
        ConsultDetailsModel.Result.PtReserve parent = (ConsultDetailsModel.Result.PtReserve) parentListItem;
        consultParentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(ConsultChildViewHolder consultChildViewHolder, int position, Object childListItem) {
        ConsultChildModel child = (ConsultChildModel) childListItem;
        consultChildViewHolder.bind(child);
    }
}
