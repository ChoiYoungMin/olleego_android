package com.edn.olleego.adapter.mycenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.mycenter.ReservationDetailsActivity;
import com.edn.olleego.adapter.alliance.AllianceClassListAdapter;
import com.edn.olleego.common.GlobalFont;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceFacilitiesModel;
import com.edn.olleego.model.mycenter.ReservationMainSubModel;
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
public class ReservationMainSubListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private List<ReservationMainSubModel> list;
    private ReservationMainSubModel result;

    public ReservationMainSubListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        list = new ArrayList<>();
    }

    public void setData(List<ReservationMainSubModel> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public class ReservationListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.content)
        TextView content;

        public ReservationListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(position);
            if (result != null) {
                day.setText(result.getIndex() + "일차");

                int imageResourcesId = R.drawable.alert_ic_event_available;
                if (result.getFlag()) { //예약
                    content.setText(Util.getExpire(result.getContent(), 1));
                } else { //상담
                    content.setText(result.getContent());
                    content.setTextColor(Color.parseColor("#606060"));
                    imageResourcesId = R.drawable.alert_ic_insert_comment_copy;
                }
                icon.setImageResource(imageResourcesId);

                TagInfo info = new TagInfo();
                info.setReserveId(result.getReserveId());
                info.setFlag(result.getFlag());
                cardView.setTag(info);
            }
        }

        @OnClick({R.id.cardView})
        public void onClickCard(View view) {
            TagInfo info = (TagInfo) view.getTag();
            Intent intent = new Intent(mContext, ReservationDetailsActivity.class);
            intent.putExtra("reserveId", info.getReserveId());
            if (!info.getFlag()) //상담 아이템 일 경우 pageIndex = 1
                intent.putExtra("pageIndex", 1);
            mContext.startActivity(intent);
        }
    }

    class TagInfo {
        private int reserveId;
        private boolean flag;

        public boolean getFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public int getReserveId() {
            return reserveId;
        }

        public void setReserveId(int reserveId) {
            this.reserveId = reserveId;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_reservation_list_item, null);
        ReservationListHolder holder = new ReservationListHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ReservationListHolder vh = (ReservationListHolder) holder;
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