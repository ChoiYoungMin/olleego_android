package com.edn.olleego.server.request;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Antonio on 2016-08-01.
 */
public class Foods {

    String sort;
    ArrayList<String> food = new ArrayList<String>();
    String memo;
    String satiety;

    public Foods(String sort, ArrayList<String> food, String memo, String satiety) {
        this.sort = sort;
        this.food = food;
        this.memo = memo;
        this.satiety = satiety;
    }

}
