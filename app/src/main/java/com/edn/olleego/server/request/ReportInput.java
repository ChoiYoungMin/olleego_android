package com.edn.olleego.server.request;

/**
 * Created by Antonio on 2016-08-08.
 */
public class ReportInput {

    int height;
    int weight;
    int hip;
    int waist;
    int blood_sugar;
    blood_pressure blood_pressure;
    int body_fat;
    int body_fat_per;
    int muscle;


    public ReportInput(int height, int weight, int hip, int waist, int blood_sugar, blood_pressure blood_pressure, int body_fat, int body_fat_per, int muscle) {

        this.height= height;
        this.weight = weight;
        this.hip = hip;
        this.waist = waist;
        this.blood_sugar = blood_sugar;
        this.blood_pressure = blood_pressure;
        this.body_fat = body_fat;
        this.body_fat_per = body_fat_per;
        this.muscle = muscle;
    }

}
