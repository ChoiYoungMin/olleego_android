package com.edn.olleego.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by pc on 2016-08-21.
 */
public class Util {
    public static void setPreferencesString(Context context, String key, String value) {
        SharedPreferences pref = null;
        pref = context.getSharedPreferences("olleego", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = pref.edit();
        prefEditor.putString(key, value);
        prefEditor.commit();
    }

    public static String getPreferencesString(Context context, String key) {
        String returnValue = "";
        SharedPreferences pref = null;
        pref = context.getSharedPreferences("olleego", Context.MODE_PRIVATE);
        returnValue = pref.getString(key, "");
        return returnValue;
    }

    public static String makeStringComma(String str) {
        if (str.length() == 0)
            return "";
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(str);
    }


    public static String getCurrency(int price) {
        String formatted = NumberFormat.getCurrencyInstance().format((price));
        return formatted;
    }

    public static String getExpire(String expire, int formatType) {
        Date date = new Date();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        originalFormat.setTimeZone(TimeZone.getTimeZone("GMT"));// +9를 더한 시간
        try {
            date = originalFormat.parse(expire);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (formatType == 1)
            simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (HH:mm)");

        String expireDate = simpleDateFormat.format(date);
        return expireDate;
    }

    public static int getDday(String expire) {
        int mYear = 0, mMonth = 0, mDay = 0;
        int mYear2 = 0, mMonth2 = 0, mDay2 = 0;

        Date date = new Date();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        originalFormat.setTimeZone(TimeZone.getTimeZone("GMT"));// +9를 더한 시간
        try {
            date = originalFormat.parse(expire);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String year = yearFormat.format(date);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String month = monthFormat.format(date);
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
        String day = dayFormat.format(date);

        if (year != null && !TextUtils.isEmpty(year))
            mYear = Integer.parseInt(year);
        if (month != null && !TextUtils.isEmpty(month))
            mMonth = Integer.parseInt(month);
        if (day != null && !TextUtils.isEmpty(day))
            mDay = Integer.parseInt(day);

        long now = System.currentTimeMillis();
        Date date2 = new Date(now);
        SimpleDateFormat yearFormat2 = new SimpleDateFormat("yyyy");
        String year2 = yearFormat2.format(date2);
        SimpleDateFormat monthFormat2 = new SimpleDateFormat("MM");
        String month2 = monthFormat2.format(date2);
        SimpleDateFormat dayFormat2 = new SimpleDateFormat("dd");
        String day2 = dayFormat2.format(date2);

        if (year2 != null && !TextUtils.isEmpty(year2))
            mYear2 = Integer.parseInt(year2);
        if (month2 != null && !TextUtils.isEmpty(month2))
            mMonth2 = Integer.parseInt(month2);
        if (day2 != null && !TextUtils.isEmpty(day2))
            mDay2 = Integer.parseInt(day2);

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(mYear2, mMonth2, mDay2);
        c2.set(mYear, mMonth, mDay);
        long d1 = c1.getTime().getTime();
        long d2 = c2.getTime().getTime();
        int days = (int) ((d2 - d1) / (1000 * 60 * 60 * 24));
        return days;
    }
}
