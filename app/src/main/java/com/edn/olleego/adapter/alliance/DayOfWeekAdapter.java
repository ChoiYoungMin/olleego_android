package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 1..
 * <p/>
 * 제휴센터 상세 페이지 > 하단 구매하기 > 구매 상세 페이지 > 요일 선택
 */
public class DayOfWeekAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Resources res;
    private int focusedItem;
    private SparseBooleanArray selectedItems;
    private List<String> list = new ArrayList<>();
    private DayOfWeekAdapterListener listener;

    public interface DayOfWeekAdapterListener {
        void setSelectedItem(SparseBooleanArray array);
    }

    public DayOfWeekAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        selectedItems = new SparseBooleanArray();

        try {
            listener = (DayOfWeekAdapterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement DayOfWeekAdapterListener.");
        }
    }

    public void setData(List<String> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public class DayOfWeekListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.layout)
        RelativeLayout layout;

        public DayOfWeekListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            day.setText(list.get(position));

            if (selectedItems.get(focusedItem)){
                layout.setBackgroundResource(R.drawable.alliance_purchase_day_of_week_bg_ov);
                day.setTextColor(Color.parseColor("#ffffff"));
            } else{
                layout.setBackgroundResource(R.drawable.alliance_purchase_day_of_week_bg);
                day.setTextColor(Color.parseColor("#cbcbc9"));
            }
        }

        @OnClick({R.id.layout})
        public void onClickItem(View v) {
            focusedItem = getLayoutPosition();
            if (selectedItems.get(focusedItem))
                selectedItems.put(focusedItem, false);
            else
                selectedItems.put(focusedItem, true);
            notifyItemChanged(focusedItem);

            listener.setSelectedItem(selectedItems);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_purchase_day_of_week_item, null);
        DayOfWeekListHolder alliancePTListHolder = new DayOfWeekListHolder(v);
        return alliancePTListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            DayOfWeekListHolder vh = (DayOfWeekListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.size();
        return count;
    }
}