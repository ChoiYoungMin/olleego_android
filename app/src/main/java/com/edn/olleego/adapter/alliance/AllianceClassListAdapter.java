package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edn.olleego.R;
import com.edn.olleego.model.alliance.AllianceDetailsModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kym on 2016. 8. 1..
 *
 * 제휴센터 상세 페이지 > 센터안내 탭 > 수업 리스트
 */
public class AllianceClassListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private List<AllianceDetailsModel> list = new ArrayList<>();
    private AllianceDetailsModel.Result result;

    public AllianceClassListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
    }

    public void setData(List<AllianceDetailsModel> data) {
        if (data != null) list = data;

        notifyDataSetChanged();
    }

    public class AllianceClassListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.class_image)
        ImageView image;
        @BindView(R.id.class_name)
        TextView name;

        public AllianceClassListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            result = list.get(0).getResult();
            int index = 0;

            int id = result.getCenter_binfo().getClasses().get(position).get_id();
            String mName = result.getCenter_binfo().getClasses().get(position).getValue();
            name.setText(mName);

            if (id == 118) index = 0;
            else if (id == 119) index = 1;
            else if (id == 120) index = 2;

            TypedArray typedArray = res.obtainTypedArray(R.array.class_image_array);
            Glide.with(mContext)
                    .load(typedArray.getResourceId(index, 0))
                    .asBitmap()
                    .centerCrop()
                    .into(image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_center_class_item, null);
        AllianceClassListHolder allianceClassListHolder = new AllianceClassListHolder(v);
        return allianceClassListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            AllianceClassListHolder vh = (AllianceClassListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (list != null && list.size() > 0)
            count = list.get(0).getResult().getCenter_binfo().getClasses().size();
        return count;
    }
}