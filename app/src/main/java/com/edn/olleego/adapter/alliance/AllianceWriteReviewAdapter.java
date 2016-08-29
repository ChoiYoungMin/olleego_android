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
 * 제휴센터 상세 페이지 > 리뷰 탭 리뷰 작성 페이지 트레이너 리스트
 */
public class AllianceWriteReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Resources res;
    private int focusedItem;
    private SparseBooleanArray selectedItems;
    private List<AllianceTrainersModel> list = new ArrayList<>();
    private AllianceTrainersModel.Result result;
    private WriteReviewAdapterListener listener;

    public interface WriteReviewAdapterListener {
        void setReviewData(int id);
    }

    public AllianceWriteReviewAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
        selectedItems = new SparseBooleanArray();
        //selectedItems.put(0, true); //첫번째 기본 값 선택

        try {
            listener = (WriteReviewAdapterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement WriteReviewAdapterListener.");
        }
    }

    public void setData(AllianceTrainersModel data, int trainerId) {
        list.add(data);
        notifyDataSetChanged();

        if(trainerId > 0){
            int position = 0, cnt = 0;
            for (AllianceTrainersModel.Result result : data.getResult()) {
                if (result.get_id() == trainerId) {
                    position = cnt;
                    break;
                }
                cnt++;
            }
            focusedItem = position;
            selectedItems.put(focusedItem, true);
            notifyItemChanged(position);
        }
    }

    public class WriteReviewTrainerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.selected_layout)
        FrameLayout selectedImage;

        public WriteReviewTrainerViewHolder(View view) {
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
            listener.setReviewData(info.getTrainerId());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_write_review_trainers_item, null);
        WriteReviewTrainerViewHolder viewHolder = new WriteReviewTrainerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof WriteReviewTrainerViewHolder) {
                WriteReviewTrainerViewHolder vh = (WriteReviewTrainerViewHolder) holder;
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

        public int getTrainerId() {
            return trainerId;
        }

        public void setTrainerId(int trainerId) {
            this.trainerId = trainerId;
        }
    }
}