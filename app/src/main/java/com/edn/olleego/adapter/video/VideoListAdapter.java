package com.edn.olleego.adapter.video;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.activity.video.VideoDetailsActivity;
import com.edn.olleego.model.video.VideoListModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 1..
 */
public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<VideoListModel> list;

    public VideoListAdapter(Context context, Fragment fragment) {
        this.mContext = context;
        list = new ArrayList<>();
    }

    public void setData(ArrayList<VideoListModel> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public class VideoListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.sub_title)
        TextView subTitle;
        @BindView(R.id.image)
        ImageView image;

        public VideoListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            title.setText(list.get(0).getResult().get(position).getTitle());
            cardView.setTag(list.get(0).getResult().get(position).get_id());
            Glide.with(mContext)
                    .load(list.get(0).getResult().get(position).getThum_jpg())
                    .asBitmap()
                    .centerCrop()
                    .into(image);
        }

        @OnClick({R.id.cardview})
        public void onClickItem(View v) {
            Intent intent = new Intent(mContext, VideoDetailsActivity.class);
            intent.putExtra("title", title.getText().toString());
            intent.putExtra("id", v.getTag().toString());
            mContext.startActivity(intent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_video_list_item, null);
        VideoListHolder videoListHolder = new VideoListHolder(v);
        return videoListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            VideoListHolder vh = (VideoListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list.size() > 0)
            count = list.get(0).getResult().size();
        return count;
    }
}