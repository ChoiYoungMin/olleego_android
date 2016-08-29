package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.alliance.AllianceDetailsActivity;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.alliance.AllianceListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 1..
 * <p>
 * 제휴센터 리스트 Adapter
 */
public class AllianceCenterListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private final static int LIST_VIEW = 100;
    private final static int LOADING_VIEW = 200;
    private Context mContext;
    private List<AllianceListModel> list;
    private AllianceListModel.Result result;
    private Resources res;

    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public AllianceCenterListAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        res = mContext.getResources();
        list = new ArrayList<>();

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            showProgressBar();
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void showProgressBar() {
        list.get(0).getResult().add(null);
        notifyItemInserted(list.get(0).getResult().size());
    }

    public void hideProgressBar() {
        if (list != null && list.size() > 0 && list.get(0).getResult().size() > 0
                && list.get(0).getResult().get(list.get(0).getResult().size() - 1) == null) {

            list.get(0).getResult().remove(list.get(0).getResult().size() - 1);
            notifyItemRemoved(list.get(0).getResult().size());
        }
    }

    public void setData(ArrayList<AllianceListModel> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void addData(ArrayList<AllianceListModel> data) {
        list.get(0).getResult().addAll(data.get(0).getResult());
        notifyItemInserted(list.get(0).getResult().size() - 1);
        setLoaded();
    }

    public class AllianceListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.category)
        TextView category;
        @BindView(R.id.location)
        TextView location;
        @BindView(R.id.place)
        TextView place;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.review_count)
        TextView reviewCount;
        @BindView(R.id.pt)
        TextView pt;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.sale_money)
        TextView saleMoney;
        @BindView(R.id.star)
        RatingBar ratingBar;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.discount)
        ImageView discount;

        public AllianceListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult().get(position);

            int price = 0;
            if (result.getPt() != null) {
                price = result.getPt().getMoney();
                pt.setText(result.getPt().getTitle());
            }

            boolean saleFlag = result.isSale_flag();
            if (saleFlag) {
                discount.setVisibility(View.VISIBLE);
                price = result.getSale_money();
                saleMoney.setText(Util.getCurrency(price));
                saleMoney.setPaintFlags(saleMoney.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                discount.setVisibility(View.GONE);
            }

            int colorResourceId = res.getColor(R.color.category_fitness);

            if (result.getCenter_binfo() != null) {
                if (result.getCenter_binfo().getType() != null) {
                    int id = result.getCenter_binfo().getType().get_id();

                    if (id == 301) colorResourceId = res.getColor(R.color.category_fitness);
                    else if (id == 303) colorResourceId = res.getColor(R.color.category_ptshop);
                    else if (id == 305) colorResourceId = res.getColor(R.color.category_yoga);

                    category.setTextColor(colorResourceId);
                    category.setText(result.getCenter_binfo().getType().getValue());
                }

                location.setText(result.getCenter_binfo().getLocate());
                place.setText("(" + result.getCenter_binfo().getPlace() + ")");
                name.setText(result.getCenter_binfo().getName());

                Glide.with(mContext)
                        .load(result.getCenter_binfo().getImage())
                        .asBitmap()
                        .centerCrop()
                        .into(image);
            }

            ratingBar.setRating(result.getPoint_avg());
            reviewCount.setText("(" + result.getReview_count() + ")");
            money.setText(Util.getCurrency(price));
            cardView.setTag(result.get_id());
        }

        @OnClick({R.id.cardview})
        public void onClickItem(View v) {
            int id = (int) v.getTag();
            Intent intent = new Intent(mContext, AllianceDetailsActivity.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        public ProgressViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            progressBar.setIndeterminate(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == LOADING_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_center_list_progress_item, null);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(v);
            return progressViewHolder;
        }
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_center_list_item, null);
        AllianceListHolder allianceListHolder = new AllianceListHolder(v);
        return allianceListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {

            if (holder instanceof AllianceListHolder) {
                AllianceListHolder vh = (AllianceListHolder) holder;
                vh.bindView(position);

            } else if (holder instanceof ProgressViewHolder) {
                ProgressViewHolder vh = (ProgressViewHolder) holder;
                vh.bindView(position);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(0).getResult().get(position) != null ? LIST_VIEW : LOADING_VIEW;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}