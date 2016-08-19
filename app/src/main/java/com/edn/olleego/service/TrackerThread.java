package com.edn.olleego.service;

import android.os.Handler;

/**
 * Created by Antonio on 2016-08-19.
 */
public class TrackerThread extends Thread{
    Handler handler;
    boolean isRun = true;

    public TrackerThread(Handler handler){
        this.handler = handler;
    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
            try{
            }catch (Exception e) {}
        }
    }
}