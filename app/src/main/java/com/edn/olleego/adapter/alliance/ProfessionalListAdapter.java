package com.edn.olleego.adapter.alliance;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edn.olleego.R;
import com.edn.olleego.model.alliance.AllianceTrainersModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kym on 2016. 8. 1..
 *
 * 제휴센터 상세 > 세부정보 탭 > 트레이너 전문 분야 가로 리스트 어댑터
 */
public class ProfessionalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Resources res;
    private List<AllianceTrainersModel.Result.BasicInfo.Career.Professional> list = new ArrayList<>();

    public ProfessionalListAdapter(Context context) {
        this.mContext = context;
        res = mContext.getResources();
    }

    public void setData(List<AllianceTrainersModel.Result.BasicInfo.Career.Professional> data) {
        if (data != null) list = data;

        notifyDataSetChanged();
    }

    public class ProfessionalListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        public ProfessionalListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(int position) {
            // Data set..
            name.setText(list.get(position).getValue());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_trainer_professional_list_item, null);
        ProfessionalListHolder professionalListHolder = new ProfessionalListHolder(v);
        return professionalListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ProfessionalListHolder vh = (ProfessionalListHolder) holder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
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