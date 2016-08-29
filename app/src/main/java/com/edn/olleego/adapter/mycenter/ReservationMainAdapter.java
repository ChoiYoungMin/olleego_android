package com.edn.olleego.adapter.mycenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceCenterActivity;
import com.edn.olleego.activity.mycenter.ReservationDetailsActivity;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.mycenter.RecommendedFitnessModel;
import com.edn.olleego.model.mycenter.ReservationMainSubModel;
import com.edn.olleego.model.mycenter.ReservationModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 1..
 */
public class ReservationMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private final static int LIST_VIEW = 100;
    private final static int BOTTOM_VIEW = 200;
    private Context mContext;
    private Resources res;
    private List<ReservationModel.Result> list;
    private ReservationModel.Result result;
    private ReservationMainSubListAdapter reservationMainSubListAdapter;

    public ReservationMainAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        list = new ArrayList<>();
    }

    public void setData(List<ReservationModel.Result> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public class ReservationListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.d_day)
        TextView dDay;
        @BindView(R.id.expire)
        TextView expire;
        @BindView(R.id.total)
        TextView total;
        @BindView(R.id.remainder)
        TextView remainder;
        @BindView(R.id.reserve_count)
        TextView reserveCount;
        @BindView(R.id.consultCount)
        TextView consultCount;
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;
        @BindView(R.id.layout_reservation)
        RelativeLayout reservation;
        @BindView(R.id.layout_consult)
        RelativeLayout consult;


        public ReservationListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(position);
            name.setText(result.getCenter().getCenter_binfo().getName());
            dDay.setText(Util.getDday(result.getPt_expire()) + res.getString(R.string.d_day_text));
            expire.setText("(" + Util.getExpire(result.getPt_expire(), 0) + res.getString(R.string.expire_day_text) + ")");
            total.setText(String.valueOf(result.getPt_total()));
            remainder.setText(String.valueOf(result.getPt_remain()));

            reserveCount.setText(String.valueOf(result.getReserve_count()));
            consultCount.setText(String.valueOf(result.getConsult_count()));

            TagInfo info = new TagInfo();
            info.setReserveId(result.get_id());
            reservation.setTag(info);
            consult.setTag(info);

            List<ReservationMainSubModel> list = new ArrayList<>();
            for (ReservationModel.Result.PtReserve model : result.getPt_reserve()) {
                if (model != null) {
                    ReservationMainSubModel sub = new ReservationMainSubModel();
                    sub.setFlag(true);
                    sub.setIndex(model.getIndex());
                    sub.setContent(model.getReserve_start());
                    sub.setReserveId(result.get_id());
                    list.add(sub);
                }
            }
            for (ReservationModel.Result.PtConsult model : result.getPt_consult()) {
                if (model != null) {
                    ReservationMainSubModel sub = new ReservationMainSubModel();
                    sub.setFlag(false);
                    sub.setIndex(model.getIndex());
                    sub.setContent(model.getConsult_text());
                    sub.setReserveId(result.get_id());
                    list.add(sub);
                }
            }
            reservationMainSubListAdapter.setData(list);
        }

        @OnClick({R.id.layout_reservation, R.id.layout_consult})
        public void onClickItem(View view) {
            TagInfo info = (TagInfo) view.getTag();
            int reserveId = info.getReserveId();
            int id = view.getId();
            Intent intent = new Intent(mContext, ReservationDetailsActivity.class);
            intent.putExtra("reserveId", reserveId);
            if (id == R.id.layout_consult)
                intent.putExtra("pageIndex", 1);
            mContext.startActivity(intent);
        }

        class TagInfo {
            private int reserveId;

            public int getReserveId() {
                return reserveId;
            }

            public void setReserveId(int reserveId) {
                this.reserveId = reserveId;
            }
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.location)
        TextView location;

        @BindView(R.id.title2)
        TextView title2;
        @BindView(R.id.money2)
        TextView money2;
        @BindView(R.id.location2)
        TextView location2;
        @BindView(R.id.center_shortcut)
        RelativeLayout center_shortcut;

        public BottomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            String token = "Olleego " + Util.getPreferencesString(mContext, "login_token");

            RestfulAdapter.setOlleegoInterface(null);
            Call<RecommendedFitnessModel> call = RestfulAdapter.getInstance().getRecommend(token);
            call.enqueue(new Callback<RecommendedFitnessModel>() {
                @Override
                public void onResponse(Call<RecommendedFitnessModel> call, final retrofit2.Response<RecommendedFitnessModel> response) {
                    boolean isSuccessful = response.isSuccessful();
                    Log.d(TAG, "isSuccessful : " + isSuccessful);
                    if (isSuccessful) { // 하단 추천상품
                        List<RecommendedFitnessModel.Result> list = response.body().getResult();
                        if (list != null && list.size() > 0) {
                            title.setText(list.get(0).getTitle());
                            money.setText(Util.getCurrency(list.get(0).getMoney()));
                            location.setText(list.get(0).getCenter().getCenter_binfo().getLocate());

                            title2.setText(list.get(1).getTitle());
                            money2.setText(Util.getCurrency(list.get(1).getMoney()));
                            location2.setText(list.get(1).getCenter().getCenter_binfo().getLocate());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RecommendedFitnessModel> call, Throwable t) {
                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                }
            });
        }

        @OnClick({R.id.center_shortcut})
        public void onClickItem(View v) { //제휴센터 바로가기 버튼
            Intent intent = new Intent(mContext, AllianceCenterActivity.class);
            mContext.startActivity(intent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == BOTTOM_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_reservation_bottom_view_item, null);
            BottomViewHolder holder = new BottomViewHolder(v);
            return holder;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_reservation_item, null);
        ReservationListHolder holder = new ReservationListHolder(v);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        reservationMainSubListAdapter = new ReservationMainSubListAdapter(mContext);
        holder.recyclerView.setAdapter(reservationMainSubListAdapter);
        holder.recyclerView.setNestedScrollingEnabled(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof ReservationListHolder) {
                ReservationListHolder vh = (ReservationListHolder) holder;
                vh.bindView(position);
            } else if (holder instanceof BottomViewHolder) {
                BottomViewHolder vh = (BottomViewHolder) holder;
                vh.bindView(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? LIST_VIEW : BOTTOM_VIEW;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 1)
            count = list.size();
        return count;
    }
}