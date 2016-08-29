package com.edn.olleego.adapter.alliance;

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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceWriteReviewActivity;
import com.edn.olleego.common.RestfulAdapter;
import com.edn.olleego.model.alliance.AllianceDeleteReviewBody;
import com.edn.olleego.model.alliance.AllianceReviewModel;
import com.edn.olleego.model.alliance.AllianceWriteReviewResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kym on 2016. 8. 1..
 * <p/>
 * 제휴센터 상세 페이지 > 리뷰 탭 ListAdapter
 */
public class AllianceReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private final static int LIST_VIEW = 100;
    private final static int FOOTER_VIEW = 200;

    private Context mContext;
    private Resources res;
    private List<AllianceReviewModel> list = new ArrayList<>();
    private AllianceReviewModel.Result result;
    private ReviewAdapterListener listener;
    private String userId;
    private String token;

    public interface ReviewAdapterListener {
        void onClickMore();

        void setEmptyView();

        void setItemCount(int itemCount);
    }

    public AllianceReviewAdapter(Context context, Fragment fragment, String userId, String token) {
        this.mContext = context;
        this.userId = userId;
        this.token = token;
        res = mContext.getResources();

        try {
            listener = (ReviewAdapterListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement ReviewAdapterListener.");
        }
    }

    public void setData(AllianceReviewModel data) {
        list.add(data);
        notifyDataSetChanged();
    }

    public void addData(AllianceReviewModel data) {
        //footer를 제외한 마지막 index에 item 추가
        list.get(0).getResult().addAll(list.get(0).getResult().size() - 2, data.getResult());
        notifyItemInserted(list.get(0).getResult().size() - 2);
    }

    public class AllianceReviewListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.count)
        TextView count;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.writer)
        TextView writer;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.imageView)
        ImageView image;
        @BindView(R.id.layout_edit)
        RelativeLayout edit;
        @BindView(R.id.layout_delete)
        RelativeLayout delete;

        public AllianceReviewListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult().get(position);
            name.setText(result.getTrainer().getName());
            ratingBar.setRating(result.getPoint());
            content.setText(result.getBody());
            writer.setText(result.getWriter().getName());

            if (userId.equals(String.valueOf(result.getWriter().get_id()))) { //내가 쓴 리뷰
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            } else {
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }

            TagInfo info = new TagInfo();
            info.setCenterId(result.getCenter().get_id());
            info.setTextId(result.get_id());
            info.setTrainerId(result.getTrainer().get_id());

            edit.setTag(info);
            delete.setTag(info);
           /* Glide.with(mContext)
                    .load(list.get(0).getResult().get(position).getAvatar())
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .centerCrop()
                    .into(image);*/
        }

        //리뷰 수정
        @OnClick({R.id.layout_edit})
        public void onClickEdit(View v) {
            TagInfo info = (TagInfo) v.getTag();
            Intent intent = new Intent(mContext, AllianceWriteReviewActivity.class);
            intent.putExtra("id", info.getCenterId());
            intent.putExtra("textId", info.getTextId());
            intent.putExtra("trainerId", info.getTrainerId());
            intent.putExtra("content", content.getText().toString());
            intent.putExtra("rating", ratingBar.getRating());
            intent.putExtra("isEdit", true);
            mContext.startActivity(intent);
        }

        //리뷰 삭제
        @OnClick({R.id.layout_delete})
        public void onClickDelete(final View view) {
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
                    TagInfo info = (TagInfo) view.getTag();
                    AllianceDeleteReviewBody body = new AllianceDeleteReviewBody();
                    body.setWriter(userId);

                    RestfulAdapter.setOlleegoInterface(null);
                    Call<AllianceWriteReviewResult> call = RestfulAdapter.getInstance().deleteReview(token, info.getTextId(), body);
                    call.enqueue(new Callback<AllianceWriteReviewResult>() {
                        @Override
                        public void onResponse(Call<AllianceWriteReviewResult> call, retrofit2.Response<AllianceWriteReviewResult> response) {
                            Log.d(TAG + "_onResponse", "isSuccessful : " + response.isSuccessful());
                            if (response.isSuccessful()) {
                                alertDialog.dismiss();

                                if (getLayoutPosition() > -1) {
                                    list.get(0).getResult().remove(getLayoutPosition());
                                    notifyItemRemoved(getLayoutPosition());

                                    listener.setItemCount(getItemCount() - 1);
                                    if (getItemCount() == 1) {
                                        // 후기 더보기를 하지 않은 상태에서 아이템 삭제시 전체 리뷰 count를 조회해서 아무것도 없을 시에 emptyView 처리해야함..
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

        }

        class TagInfo {
            private int centerId;
            private int textId;
            private int trainerId;

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
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout)
        LinearLayout layout;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
        }

        @OnClick({R.id.layout})
        public void onClick(View v) {
            listener.onClickMore();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == FOOTER_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_review_footer_view, null);
            FooterViewHolder footerViewHolder = new FooterViewHolder(v);
            return footerViewHolder;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_review_list_item, null);
        AllianceReviewListViewHolder reviewListViewHolder = new AllianceReviewListViewHolder(v);
        return reviewListViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof AllianceReviewListViewHolder) {
                AllianceReviewListViewHolder vh = (AllianceReviewListViewHolder) holder;
                vh.bindView(position);
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder vh = (FooterViewHolder) holder;
                vh.bindView(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1)
            return FOOTER_VIEW;
        return LIST_VIEW;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }
}