package com.edn.olleego.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

import com.edn.olleego.R;
import com.edn.olleego.activity.diary.DiaryFoodActivity;
import com.soundcloud.android.crop.Crop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Antonio on 2016-07-29.
 */
public class DiaryFoodImageDialog extends Dialog {

    @BindView(R.id.dialog_food_image_camera)
    RadioButton dialog_food_image_camera;

    @BindView(R.id.dialog_food_image_gallery)
    RadioButton dialog_food_image_gallery;

    Activity activity;

    public DiaryFoodImageDialog(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialog_food_image);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.dialog_food_no)
    void food_no_btn_click() {
        dismiss();
    }

    @OnClick(R.id.dialog_food_ok)
    void food_ok_btn_click() {


        //
        if(dialog_food_image_camera.isChecked() == true) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(intent,1);
            dismiss();

        } if(dialog_food_image_gallery.isChecked() == true) {
            Crop.pickImage(activity);
            dismiss();
        } else {
            Toast.makeText(getContext(), "선택하세요.", Toast.LENGTH_SHORT).show();
        }

    }

}
