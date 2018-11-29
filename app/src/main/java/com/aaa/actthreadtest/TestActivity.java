package com.aaa.actthreadtest;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    
    Thread thread1;
    volatile boolean isRunning = true;

    MemoryVo memoryVo = new MemoryVo();
    
    OuterThread outerThread;
    OuterListenerThread outerListenerThread;
    
    Button btn1,btn2, btn3, btn4, btnGC;
    private final MyHandler mHandler = new MyHandler(this);
    
    OuterListener outerListener = new OuterListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        btn1 = findViewById(R.id.btnThread1);
        btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btnThread2);
        btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btnThread3);
        btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btnThread4);
        btn4.setOnClickListener(this);
        btnGC = findViewById(R.id.btnGc);
        btnGC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnThread1:{
                startInnerThread();
                break;
            }
            case R.id.btnThread2:{
                outerThread = new OuterThread(mHandler);
                outerThread.start();
                break;
            }
            case R.id.btnThread3:{
                
                outerListenerThread = new OuterListenerThread();
                outerListenerThread.setListener(new ActListener() {
                    @Override
                    public void onReceiveMsg(Message msg) {
                        System.out.println(TestActivity.this+"=======listener======="+msg);
                    }
                });
                outerListenerThread.start();
                break;
            }
            case R.id.btnThread4:{
                outerListenerThread = new OuterListenerThread();
                outerListenerThread.setListener(outerListener);
                outerListenerThread.start();
                break;
            }
            case R.id.btnGc:{
                Runtime.getRuntime().gc();
                break;
            }
        }
    }

    private void startInnerThread() {
        thread1 = new Thread(){
            @Override
            public void run() {
                int i = 0;
                while (isRunning){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String str = "===t1===" + ++i;
//                    System.out.println(TestActivity.this + str);
                    if(mHandler.getActivity() != null) {
                        Message msg = Message.obtain();
                        msg.what = R.id.btnThread1;
                        msg.obj = str;
                        mHandler.sendMessage(msg);
//                        System.out.println(str);
                    }else {
                        System.out.println("t1=====act======null");
                    }
                    
                }
            }
        };
        thread1.start();
    }

    @Override
    protected void onDestroy() {
//        isRunning = false;//不主动关闭inner thread，子线程持有activity引用，子线程一直运行着，activity就永远不会被gc
//        outerThread.close();//不主动关闭outer thread，子线程持有 handler的弱引用，handler持有activity的弱引用，activity关闭后，主动GC，handler和activity对象都能被回收，子线程仍然运行着
//        outerListenerThread.close();//不主动关闭outer listener thread，listener持有activity的引用，thread持有listener引用，activity关闭后，thread还在运行，listener没有释放，activity也不会被GC
        //btn4  这种情况下outerListener持有activity的弱引用，所以activity关闭后，主动GC，activity可以被回收，子线程仍然运行
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    
    public void setMsg(int what, String msg){
        switch (what){
            case R.id.btnThread1:{
                btn1.setText(msg);
                break;
            }
            case R.id.btnThread2:{
                btn2.setText(msg);
                break;
            }
            case R.id.btnThread3:{
                btn3.setText(msg);
                break;
            } 
        }
    }


    
}
