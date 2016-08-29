package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AlliancePTListModel;
import com.edn.olleego.model.alliance.AlliancePTticketModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 1..
 * <p/>
 * 제휴센터 상세 페이지 > 하단 구매하기 버튼 클릭시 pt권 리스트
 */
public class PurchasePTadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Resources res;
    private int focusedItem;
    private SparseBooleanArray selectedItems;
    private List<AlliancePTListModel> list = new ArrayList<>();
    private AlliancePTListModel.Result result;
    private PurchasePTadapterListener listener;

    public interface PurchasePTadapterListener {
        void setSelectedItem(AlliancePTticketModel info);
    }

    public PurchasePTadapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        selectedItems = new SparseBooleanArray();

        try {
            listener = (PurchasePTadapterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement PurchasePTadapterListener.");
        }
    }

    public void setData(List<AlliancePTListModel> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public void clearSelection(int position){
        selectedItems.put(position, false);
        notifyItemChanged(position);
    }

    public class AlliancePTListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.expiry)
        TextView expiry;
        @BindView(R.id.layout)
        RelativeLayout layout;

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

            if (selectedItems.get(position))
                layout.setBackgroundColor(Color.parseColor("#ccd6ff"));
            else
                layout.setBackgroundColor(Color.parseColor("#ffffff"));

            AlliancePTticketModel info = new AlliancePTticketModel();
            info.setExpiry(result.getExpiry());
            info.setId(result.get_id());
            info.setMoney(result.getMoney());
            info.setSale_money(result.getSale_money());
            info.setTitle(result.getTitle());
            info.setPosition(position);

            cardView.setTag(info);
        }

        @OnClick({R.id.card_view})
        public void onClickItem(View v) {
            selectedItems.put(focusedItem, false);
            notifyItemChanged(focusedItem);
            focusedItem = getLayoutPosition();
            notifyItemChanged(focusedItem);
            selectedItems.put(focusedItem, true);

            AlliancePTticketModel info = (AlliancePTticketModel) v.getTag();
            listener.setSelectedItem(info);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_center_bottom_sheet_pt_item, null);
        AlliancePTListHolder alliancePTListHolder = new AlliancePTListHolder(v);
        return alliancePTListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            AlliancePTListHolder vh = (AlliancePTListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }
}