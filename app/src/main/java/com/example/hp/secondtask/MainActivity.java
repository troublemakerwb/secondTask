package com.example.hp.secondtask;


import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import  android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   //其它代码略
        final TextView textView = (TextView) findViewById(R.id.txtContent);
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {    //然后在主线程中处理消息
                textView.setText(msg.arg1 + "");
            }
        };
        final Runnable myWorker = new Runnable() {
            @Override

            public void run() {
                int progress = 0;
                while (progress <= 100) {
                    Message msg = new Message();
                    msg.arg1 = progress;
                    handler.sendMessage(msg);  //在新线程中发送消息
                    progress += 10;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = handler.obtainMessage();//同 new Message();
                msg.arg1 = -1;
                handler.sendMessage(msg);
            }
        };
        Button button = (Button) findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread = new Thread(null, myWorker, "WorkThread");
                workThread.start();
            }
        });
        final TextView textView1 = (TextView) findViewById(R.id.txtContent);
        Handler handler1 = new Handler();
        handler1.post(new Runnable(){     //将Runnable对象从后台线程发送给主线程
            @Override
            public void run() {
                textView1.setText("123");
            }
        });
    }
}
