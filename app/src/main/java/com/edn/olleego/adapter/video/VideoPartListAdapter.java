package com.edn.olleego.adapter.video;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edn.olleego.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kym on 2016. 8. 1..
 */
public class VideoPartListAdapter extends RecyclerView.Adapter<VideoPartListAdapter.HorizontalViewHolder> {
    private Context mContext;
    private int focusedItem;
    private String[] array, idArray;
    private SparseBooleanArray selectedItems;
    private Resources res;
    private VideoPartListener listener;

    public interface VideoPartListener {
        void onClickPartItem(String id);
    }

    public VideoPartListAdapter(Context mContext, Fragment fragment) {
        this.mContext = mContext;
        res = mContext.getResources();
        selectedItems = new SparseBooleanArray();
        selectedItems.put(0, true);
        array = mContext.getResources().getStringArray(R.array.part_list_array);
        idArray = mContext.getResources().getStringArray(R.array.part_id_array);

        try {
            listener = (VideoPartListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement VideoPartListener.");
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.part_name)
        TextView name;
        @BindView(R.id.part_layout)
        RelativeLayout layout;

        public HorizontalViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.part_layout})
        public void onClickItem(View v) {
            // Redraw the old selection and the new
            selectedItems.put(focusedItem, false);
            notifyItemChanged(focusedItem);
            focusedItem = getLayoutPosition();
            notifyItemChanged(focusedItem);
            selectedItems.put(focusedItem, true);

            listener.onClickPartItem(v.getTag().toString());
        }
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_part_horizontal_list_item, parent, false);
        HorizontalViewHolder viewHolder = new HorizontalViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        String color = "#ffffff";
        Drawable drawable = null;
        holder.name.setText(array[position]);
        if (selectedItems.get(position)) {
            color = "#000000";
            drawable = res.getDrawable(R.drawable.exercise_horizontal_item_bg_ov);
        } else
            drawable = res.getDrawable(R.drawable.exercise_horizontal_item_bg_normal);

        holder.layout.setBackground(drawable);
        holder.layout.setTag(idArray[position]);
        holder.name.setTextColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
        return array.length;
    }
}
