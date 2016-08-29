package com.edn.olleego.adapter.mycenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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
import com.edn.olleego.adapter.alliance.AllianceClassListAdapter;
import com.edn.olleego.common.GlobalFont;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceDetailsModel;
import com.edn.olleego.model.alliance.AllianceFacilitiesModel;
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
public class ReservationDetailsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private final static int HEADER_VIEW = 100;
    private final static int LIST_VIEW = 200;
    private Context mContext;
    private Resources res;
    private List<ReservationDetailsModel.Result.PtReserve> list;
    private ReservationDetailsModel.Result.PtReserve result;
    private String phoneNumber;

    public ReservationDetailsListAdapter(Context context, String phoneNumber) {
        this.mContext = context;
        res = mContext.getResources();
        list = new ArrayList<>();
        this.phoneNumber = phoneNumber;
    }

    public void setData(List<ReservationDetailsModel.Result.PtReserve> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public class ReservationListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.layout)
        RelativeLayout layout;
        @BindView(R.id.day)
        TextView day;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.status)
        TextView status;

        public ReservationListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(position);
            day.setText(result.getIndex() + res.getString(R.string.details_day));
            content.setText(Util.getExpire(result.getReserve_start(), 1));

            boolean flag = false;
            // 예약 상태 : RR 예약 / RC 예약취소  / RS 완료  / RU 변경
            String stat = result.getReserve_status();
            String text = res.getString(R.string.details_status_change);
            String statusColor = "#cbcbc9";
            String contentsColor = "#606060";
            String backGroundColor = "#ffffff";
            if (stat.equals("RS")) { //완료
                text = res.getString(R.string.details_status_complete);
                statusColor = "#cbcbc9";
                backGroundColor = "#f0f1ed";
                contentsColor = "#cbcbc9";
            } else if (stat.equals("RR")) { // 예약
                flag = true;
                text = res.getString(R.string.details_status_change);
                statusColor = "#5e7efc";
                backGroundColor = "#ffffff";
            } else if (stat.equals("RC")) { //취소
                text = res.getString(R.string.details_status_cancel);
                statusColor = "#ff5b53";
                backGroundColor = "#f0f1ed";
                contentsColor = "#cbcbc9";
            }
            status.setText(text);
            status.setTextColor(Color.parseColor(statusColor));
            status.setTag(flag);
            day.setTextColor(Color.parseColor(contentsColor));
            content.setTextColor(Color.parseColor(contentsColor));
            layout.setBackgroundColor(Color.parseColor(backGroundColor));
        }

        @OnClick({R.id.status})
        public void onClickItem(View view) {
            boolean flag = (boolean) view.getTag();
            if(flag){
                callTrainer(); //예약 상태인 리스트 클릭시 트레이너에게 전화 걸기 팝업 띄움
            }
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.call_trainer)
        LinearLayout call;

        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
        }

        @OnClick({R.id.call_trainer}) //트레이너에게 전화하기
        public void onClickCall(final View view) {
            callTrainer();
        }
    }

    void callTrainer(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.review_custom_dialog, null);
        RelativeLayout cancel = (RelativeLayout) dialogView.findViewById(R.id.layout_cancel);
        RelativeLayout confirm = (RelativeLayout) dialogView.findViewById(R.id.layout_ok);
        TextView message = (TextView) dialogView.findViewById(R.id.text_message);
        TextView call = (TextView) dialogView.findViewById(R.id.text_ok);
        message.setText(res.getString(R.string.alert_text_call_trainer));
        call.setText(res.getString(R.string.dialog_confirm));
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                mContext.startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == HEADER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_reservation_details_list_header_item, null);
            HeaderViewHolder holder = new HeaderViewHolder(v);
            return holder;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_reservation_details_list_item, null);
        ReservationListHolder holder = new ReservationListHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof ReservationListHolder) {
                ReservationListHolder vh = (ReservationListHolder) holder;
                vh.bindView(position);
            } else if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder vh = (HeaderViewHolder) holder;
                vh.bindView(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? LIST_VIEW : HEADER_VIEW;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.size();
        return count;
    }
}