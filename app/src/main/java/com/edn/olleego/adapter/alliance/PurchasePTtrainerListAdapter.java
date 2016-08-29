package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.model.alliance.AllianceTrainersModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by kym on 2016. 8. 1..
 * <p>
 * 제휴센터 상세 페이지 > 하단 구매하기 > 구매하기 페이지 > 트레이너 선택
 */
public class PurchasePTtrainerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Resources res;
    private int focusedItem;
    private SparseBooleanArray selectedItems;
    private List<AllianceTrainersModel> list = new ArrayList<>();
    private AllianceTrainersModel.Result result;
    private TrainerListAdapterListener listener;

    public interface TrainerListAdapterListener {
        void setTrainerInfo(int trainerId, String name, String avatar);
    }

    public PurchasePTtrainerListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        selectedItems = new SparseBooleanArray();

        try {
            listener = (TrainerListAdapterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement TrainerListAdapterListener.");
        }
    }

    public void setData(AllianceTrainersModel data) {
        list.add(data);
        notifyDataSetChanged();
    }

    public class TrainerListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.selected_layout)
        FrameLayout selectedImage;

        public TrainerListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult().get(position);

            name.setText(result.getName());

            Glide.with(mContext)
                    .load(result.getAvatar())
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .centerCrop()
                    .into(image);

            String color = "#cbcbc9";
            selectedImage.setVisibility(View.GONE);
            if (selectedItems.get(position)) {
                color = "#5e7efc";
                selectedImage.setVisibility(View.VISIBLE);
            }
            name.setTextColor(Color.parseColor(color));

            TagInfo info = new TagInfo();
            info.setTrainerId(result.get_id());
            info.setAvatar(result.getAvatar());
            info.setName(result.getName());
            layout.setTag(info);
        }

        @OnClick({R.id.layout})
        public void onClickIcon(View view) {
            selectedItems.put(focusedItem, false);
            notifyItemChanged(focusedItem);
            focusedItem = getLayoutPosition();
            notifyItemChanged(focusedItem);
            selectedItems.put(focusedItem, true);

            TagInfo info = (TagInfo) view.getTag();
            listener.setTrainerInfo(info.getTrainerId(), info.getName(), info.getAvatar());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_write_review_trainers_item, null);
        TrainerListViewHolder viewHolder = new TrainerListViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof TrainerListViewHolder) {
                TrainerListViewHolder vh = (TrainerListViewHolder) holder;
                vh.bindView(position);
            }
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

    class TagInfo {
        private int trainerId;
        private String avatar;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTrainerId() {
            return trainerId;
        }

        public void setTrainerId(int trainerId) {
            this.trainerId = trainerId;
        }
    }
}