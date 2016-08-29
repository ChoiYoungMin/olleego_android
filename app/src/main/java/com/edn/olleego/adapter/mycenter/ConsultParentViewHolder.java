package com.edn.olleego.adapter.mycenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.edn.olleego.R;
import com.edn.olleego.common.Util;
import com.edn.olleego.model.mycenter.ConsultDetailsModel;

/**
 * Created by pc on 2016-08-22.
 */
public class ConsultParentViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    private Context mContext;
    private CardView cardView;
    private TextView day;
    private TextView content;
    private TextView consult_status;
    private ImageView image_status;
    private RelativeLayout layout;
    private Resources res;

    public ConsultParentViewHolder(View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        res = mContext.getResources();

        cardView = (CardView) itemView.findViewById(R.id.cardView);
        day = (TextView) itemView.findViewById(R.id.day);
        content = (TextView) itemView.findViewById(R.id.content);
        consult_status = (TextView) itemView.findViewById(R.id.consult_status);
        image_status = (ImageView) itemView.findViewById(R.id.image_status);
        layout = (RelativeLayout) itemView.findViewById(R.id.layout);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = (boolean) v.getTag();
                if (flag) { //상담 완료건만 상담내역 리스트를 열람 가능
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                    }
                }
            }
        });
    }


    public void bind(ConsultDetailsModel.Result.PtReserve model) {
        day.setText(model.getIndex() + res.getString(R.string.details_day));
        content.setText(Util.getExpire(model.getReserve_start(), 1));

        String text = "";
        String textColor = "#cbcbc9";
        String backGroundColor = "#ffffff";
        int imageResourcesId = R.drawable.detail_ic_insert_comment_completed;
        if (model.isConsult_confirm()) { //상담 완료
            text = res.getString(R.string.consult_status_complete);
            textColor = "#5e7efc";
            backGroundColor = "#ffffff";
            imageResourcesId = R.drawable.detail_ic_insert_comment_completed;
        } else { // 상담 대기
            text = res.getString(R.string.consult_status_waiting);
            textColor = "#cbcbc9";
            backGroundColor = "#ffffff";
            imageResourcesId = R.drawable.detail_ic_insert_comment_pending;
        }
        consult_status.setText(text);
        consult_status.setTextColor(Color.parseColor(textColor));
        day.setTextColor(Color.parseColor(textColor));
        content.setTextColor(Color.parseColor(textColor));
        layout.setBackgroundColor(Color.parseColor(backGroundColor));
        image_status.setImageResource(imageResourcesId);

        cardView.setTag(model.isConsult_confirm());
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (expanded) {
                image_status.setImageResource(R.drawable.ic_expand_less_b);
                //image_status.setRotation(ROTATED_POSITION);
            } else {
                image_status.setImageResource(R.drawable.detail_ic_insert_comment_completed);
                //image_status.setRotation(INITIAL_POSITION);
            }
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            RotateAnimation rotateAnimation;
            if (expanded) { // rotate clockwise
                rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            } else { // rotate counterclockwise
                rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                        INITIAL_POSITION,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            }

            rotateAnimation.setDuration(200);
            rotateAnimation.setFillAfter(true);
            image_status.startAnimation(rotateAnimation);
        }
    }
}
