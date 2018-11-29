package com.aaa.actthreadtest;

import android.os.Message;

import java.lang.ref.WeakReference;

public class OuterListener implements ActListener {

    //对Activity的弱引用
    private final WeakReference<TestActivity> mActivity;

    public OuterListener(TestActivity mActivity) {
        this.mActivity = new WeakReference<>(mActivity);
    }

    @Override
    public void onReceiveMsg(Message msg) {
        System.out.println(mActivity + "====" + mActivity.get()+"=======listener======="+msg);
    }
}
