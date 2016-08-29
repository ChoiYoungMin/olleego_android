package com.edn.olleego.adapter.mycenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceDetailsActivity;
import com.edn.olleego.adapter.alliance.AllianceClassListAdapter;
import com.edn.olleego.common.GlobalFont;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceFacilitiesModel;
import com.edn.olleego.model.mycenter.PurchaseListModel;
import com.edn.olleego.model.mycenter.RecommendedFitnessModel;
import com.edn.olleego.model.mycenter.ReservationDetailsModel;
import com.edn.olleego.model.mycenter.ReservationModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kym on 2016. 8. 1..
 */
public class PurchaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private List<PurchaseListModel.Result> list;
    private PurchaseListModel.Result result;

    public PurchaseListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        list = new ArrayList<>();
    }

    public void setData(List<PurchaseListModel.Result> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public class PurchaseListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.paid_at)
        TextView paid_at;
        @BindView(R.id.paid_day)
        TextView paid_day;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.pt_status)
        TextView status;
        @BindView(R.id.layout_pt_status)
        RelativeLayout statusLayout;
        @BindView(R.id.layout)
        RelativeLayout layout;
        @BindView(R.id.center_shortcut)
        TextView center_shortcut;

        public PurchaseListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(position);
            paid_at.setText(Util.getExpire(result.getPaid_at(), 0));
            name.setText(result.getPt().getCenter().getCenter_binfo().getName() + "(" + result.getPt().getCenter().getCenter_binfo().getPlace() + ")");
            title.setText(result.getPt().getTitle());
            amount.setText(Util.getCurrency(result.getPayment().getIamport().getAmount()));

            // 결재 상태 : [PR 결재 대기 PS 결재 완료 PC 결재 취소]
            // PT 상태 :  [PR : 피티 진행 , PS : 피티 완료 , PC : 도중 취소]

            if (result.getPt_status().equals("PS")) { //완료된 PT권
                statusLayout.setBackgroundColor(Color.parseColor("#cbcbc9"));
                status.setText(res.getString(R.string.completed_pt));
            }

            if (result.getPaid_status().equals("PC")) { //구매 취소
                statusLayout.setBackgroundColor(Color.parseColor("#cbcbc9"));
                status.setText(res.getString(R.string.purchase_paid_cancel));
                layout.setBackgroundColor(Color.parseColor("#f0f1ed"));
                paid_day.setTextColor(Color.parseColor("#cbcbc9"));
                name.setTextColor(Color.parseColor("#cbcbc9"));
                title.setTextColor(Color.parseColor("#cbcbc9"));
                amount.setTextColor(Color.parseColor("#cbcbc9"));
                amount.setPaintFlags(amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            center_shortcut.setTag(result.getPt().getCenter().get_id());
        }

        @OnClick({R.id.center_shortcut})
        public void onClickItem(View view) {
            int id = (int) view.getTag();
            Intent intent = new Intent(mContext, AllianceDetailsActivity.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_purchase_list_item, null);
        PurchaseListHolder holder = new PurchaseListHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            PurchaseListHolder vh = (PurchaseListHolder) holder;
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