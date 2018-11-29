package com.aaa.actthreadtest;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class OuterThread extends Thread {
    volatile boolean isRunning = true;
    private final WeakReference<Handler> mWeakHandler;

    public OuterThread(Handler mHandler) {
        this.mWeakHandler = new WeakReference<>(mHandler);
    }

    @Override
    public void run() {
        int i = 0;
        while (isRunning){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String str = "===t2===" + ++i;
//                    System.out.println(TestActivity.this + str);
            Handler myHandler = mWeakHandler.get();
            if(myHandler != null) {
                Message msg = Message.obtain();
                msg.what = R.id.btnThread2;
                msg.obj = str;
                myHandler.sendMessage(msg);
//                System.out.println(str);
            }else {
                System.out.println("t2=====act======" + myHandler);
            }
        }
    }
    
    public void close(){
        this.isRunning = false;
    }
}
