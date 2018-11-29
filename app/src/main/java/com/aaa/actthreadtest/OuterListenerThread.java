package com.aaa.actthreadtest;

import android.os.Message;

import java.lang.ref.WeakReference;

public class OuterListenerThread extends Thread {
    volatile boolean isRunning = true;
    WeakReference<ActListener> listener;

    public void setListener(ActListener listener){
        this.listener = new WeakReference<>(listener);
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
            String str = "===t3===" + ++i;
//                    System.out.println(TestActivity.this + str);
            
            if(listener != null && listener.get() != null) {
                Message msg = Message.obtain();
                msg.what = R.id.btnThread3;
                msg.obj = str;
                listener.get().onReceiveMsg(msg);
//                System.out.println(str);
            }else {
                System.out.println("t3=====act======" + listener);
            }
        }
    }
    
    public void close(){
        this.isRunning = false;
    }
}
