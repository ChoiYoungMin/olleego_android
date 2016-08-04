package com.edn.olleego.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.edn.olleego.R;
import com.edn.olleego.common.Percent;

import java.math.BigDecimal;

/**
 * Created by Antonio on 2016-07-14.
 */
public class VerticalProgressBar extends ImageView {

    /**
     * @see <a href="http://developer.android.com/reference/android/graphics/drawable/ClipDrawable.html">ClipDrawable</a>
     */
    private static final BigDecimal MAX = BigDecimal.valueOf(10000);

    public VerticalProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setImageResource(R.drawable.progress_bar);
    }

    public void setCurrentValue(Percent percent){
        int cliDrawableImageLevel = percent.asBigDecimal().multiply(MAX).intValue();
        setImageLevel(cliDrawableImageLevel);
    }


}