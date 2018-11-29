package com.aaa.actthreadtest;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class MyHandler extends Handler {

    //对Activity的弱引用
    private final WeakReference<TestActivity> mActivity;

    public MyHandler(TestActivity activity){
        mActivity = new WeakReference<>(activity);
    }

    public Activity getActivity(){
        return mActivity.get();
    }

    @Override
    public void handleMessage(Message msg) {
            weakAct(msg);
    }
    

    private void weakAct(Message msg) {
        TestActivity activity = mActivity.get();
        if(activity == null){
            super.handleMessage(msg);
            System.out.println("act=========des");
        }else{
            System.out.println("========="+msg);
            activity.setMsg(msg.what, (String) msg.obj);
        }
    }


}
