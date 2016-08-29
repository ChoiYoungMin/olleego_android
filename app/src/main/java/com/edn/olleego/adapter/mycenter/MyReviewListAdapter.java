package com.edn.olleego.adapter.mycenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceWriteReviewActivity;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceDeleteReviewBody;
import com.edn.olleego.model.alliance.AllianceWriteReviewResult;
import com.edn.olleego.model.mycenter.MyReviewListModel;

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
public class MyReviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private List<MyReviewListModel.Result> list;
    private MyReviewListModel.Result result;
    private MyReviewAdapterListener listener;

    public interface MyReviewAdapterListener {
        void setEmptyView();
    }

    public MyReviewListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        res = mContext.getResources();
        list = new ArrayList<>();

        try {
            listener = (MyReviewAdapterListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement MyReviewAdapterListener.");
        }
    }

    public void setData(List<MyReviewListModel.Result> data) {
        if (data != null) list = data;
        notifyDataSetChanged();
    }

    public class MyReviewListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;

        @BindView(R.id.trainer_image)
        ImageView trainer_image;
        @BindView(R.id.center_name)
        TextView center_name;
        @BindView(R.id.trainer_name)
        TextView trainer_name;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.review_count)
        TextView review_count;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.layout_edit)
        RelativeLayout layout_edit;
        @BindView(R.id.layout_delete)
        RelativeLayout layout_delete;

        public MyReviewListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(position);
            center_name.setText(result.getCenter().getCenter_binfo().getName() + " (" + result.getCenter().getCenter_binfo().getPlace() + ")");
            ratingBar.setRating(result.getPoint());
            content.setText(result.getBody());
            date.setText(Util.getExpire(result.getCreated(), 0));
            //review_count.setText("");

            TagInfo info = new TagInfo();

            if (result.getTrainer() != null) {
                info.setTrainerId(result.getTrainer().get_id());

                trainer_name.setText(result.getTrainer().getName());
                Glide.with(mContext)
                        .load(result.getTrainer().getAvatar())
                        .asBitmap()
                        .centerCrop()
                        .into(trainer_image);
            }

            info.setCenterId(result.getCenter().get_id());
            info.setTextId(result.get_id());
            info.setWriterId(result.getWriter().get_id());

            layout_edit.setTag(info);
            layout_delete.setTag(info);
        }

        @OnClick({R.id.layout_edit, R.id.layout_delete})
        public void onClickItem(final View view) {
            switch (view.getId()) {
                case R.id.layout_edit:
                    TagInfo info = (TagInfo) view.getTag();
                    Intent intent = new Intent(mContext, AllianceWriteReviewActivity.class);
                    intent.putExtra("id", info.getCenterId());
                    intent.putExtra("textId", info.getTextId());
                    intent.putExtra("trainerId", info.getTrainerId());
                    intent.putExtra("content", content.getText().toString());
                    intent.putExtra("rating", ratingBar.getRating());
                    intent.putExtra("isEdit", true);
                    mContext.startActivity(intent);
                    break;
                case R.id.layout_delete:
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                    View dialogView = LayoutInflater.from(mContext).inflate(R.layout.review_custom_dialog, null);
                    RelativeLayout cancel = (RelativeLayout) dialogView.findViewById(R.id.layout_cancel);
                    RelativeLayout confirm = (RelativeLayout) dialogView.findViewById(R.id.layout_ok);
                    dialogBuilder.setView(dialogView);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //String userId = Util.getPreferencesString(mContext, "user_id");
                            TagInfo info = (TagInfo) view.getTag();
                            AllianceDeleteReviewBody body = new AllianceDeleteReviewBody();
                            body.setWriter(String.valueOf(info.getWriterId()));

                            String token = "Olleego " + Util.getPreferencesString(mContext, "login_token");
                            RestfulAdapter.setOlleegoInterface(null);
                            Call<AllianceWriteReviewResult> call = RestfulAdapter.getInstance().deleteReview(token, info.getTextId(), body);
                            call.enqueue(new Callback<AllianceWriteReviewResult>() {
                                @Override
                                public void onResponse(Call<AllianceWriteReviewResult> call, retrofit2.Response<AllianceWriteReviewResult> response) {
                                    Log.d(TAG + "_onResponse", "isSuccessful : " + response.isSuccessful());
                                    if (response.isSuccessful()) {
                                        alertDialog.dismiss();

                                        if (getLayoutPosition() > -1) {
                                            list.remove(getLayoutPosition());
                                            notifyItemRemoved(getLayoutPosition());

                                            if (getItemCount() == 1) {
                                                listener.setEmptyView();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<AllianceWriteReviewResult> call, Throwable t) {
                                    Log.e(TAG + "_onFailure", "getMessage : " + t.getMessage());
                                }
                            });
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycenter_review_list_item, null);
        MyReviewListHolder holder = new MyReviewListHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            MyReviewListHolder vh = (MyReviewListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TagInfo {
        private int centerId;
        private int textId;
        private int trainerId;
        private int writerId;

        public int getWriterId() {
            return writerId;
        }

        public void setWriterId(int writerId) {
            this.writerId = writerId;
        }

        public int getCenterId() {
            return centerId;
        }

        public void setCenterId(int centerId) {
            this.centerId = centerId;
        }

        public int getTextId() {
            return textId;
        }

        public void setTextId(int textId) {
            this.textId = textId;
        }

        public int getTrainerId() {
            return trainerId;
        }

        public void setTrainerId(int trainerId) {
            this.trainerId = trainerId;
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