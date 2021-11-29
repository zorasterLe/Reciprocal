package com.example.reciprocal;


import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText input_day;
    private EditText input_hour;
    private EditText input_minute;
    private EditText input_second;
    private ProgressBar bar;
    private Button get, startTime, stop, setalarm, fresh, stopalarm;
    private TextView time;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    int status = 0;
    int i = 0;
    int key = 0;
    int totaltime = 0 ;
    int restarttotaltime = 0;
    Intent intent;
    int lable = 1;

    private Timer timer = new Timer();
    private TimerTask task = null;
    private Thread BarThread = null;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        input_day = findViewById(R.id.input_day);
        input_hour = findViewById(R.id.input_hour);
        input_minute = findViewById(R.id.input_minute);
        input_second = findViewById(R.id.input_second);
        bar =  findViewById(R.id.bar);
        get = findViewById(R.id.get);
        startTime = findViewById(R.id.starttime);
        stop = findViewById(R.id.stop);
        time = findViewById(R.id.time);
        setalarm = findViewById(R.id.setalarm);
        stopalarm = findViewById(R.id.stopalarm);
        fresh = findViewById(R.id.fresh);
        get.setOnClickListener(this);
        startTime.setOnClickListener(this);
        stop.setOnClickListener(this);
        fresh.setOnClickListener(this);
        setalarm.setOnClickListener(this);
        stopalarm.setOnClickListener(this);
        intent = getIntent();
        lable = intent.getIntExtra("lable",1);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                String day1 = input_day.getText().toString();
                String hour1 = input_hour.getText().toString();
                String minute1 = input_minute.getText().toString();
                String second1 = input_second.getText().toString();
                if(hour1.isEmpty() || minute1.isEmpty() || second1.isEmpty()){
                    Toast.makeText(MainActivity.this, "天时分秒不能为空!", Toast.LENGTH_SHORT).show();
                }

                else {
                    time.setText(day1 + ":"+hour1 + ":" + minute1 + ":" + second1);
                    day = Integer.parseInt(day1);
                    hour = Integer.parseInt(hour1);
                    minute = Integer.parseInt(minute1);
                    second = Integer.parseInt(second1);
                    int firstday = Integer.parseInt(input_day.getText().toString());
                    int firsthour = Integer.parseInt(input_hour.getText().toString());
                    int firstminute = Integer.parseInt(input_minute.getText().toString());
                    int firstsecond = Integer.parseInt(input_second.getText().toString());
                    totaltime = 86400*firstday+3600*firsthour + 60*firstminute + firstsecond;
                }
                break;
            case R.id.starttime:
                if (time.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "并未设置倒计时时间", Toast.LENGTH_SHORT).show();
                }

                else  {
                    startTime();
                    startbar();
                }
                break;
            case R.id.stop:
                if (time.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "并未设置倒计时时间", Toast.LENGTH_SHORT).show();
                }
                else {
                    stop();
                }
                break;
            case R.id.fresh:
                input_hour.setText("");
                input_minute.setText("");
                input_second.setText("");
                bar.setProgress(0);
                mediaPlayer.stop();
                recreate();
                break;
            case R.id.setalarm:
                Intent intent1 = new Intent(MainActivity.this, SetAlarm.class);
                startActivity(intent1);
                break;
            case R.id.stopalarm:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                break;

            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            time.setText(msg.obj + "");
            startTime();
        }
    };

    private Handler nHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x111){
                bar.setProgress(status);
            }

        }
    };

    public void startTime() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {

                if (day == 0 && hour == 0 && minute == 0 && second > 0 ) {
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);

                }


                if (day == 0 && hour == 0 && minute > 1 && second == 0){
                    stopTime();
                    minute--;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day == 0 && hour == 0 && minute == 1 && second == 0){
                    stopTime();
                    minute--;
                    second = 59;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day == 0 && hour > 0 && minute == 0 && second == 0){
                    stopTime();
                    hour--;
                    minute = 59;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }


                if (day == 0 && hour == 0 && minute > 0 && second > 0){
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }


                if (day == 0 && hour > 0 && minute > 0 && second == 0){
                    stopTime();
                    minute--;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day == 0 && hour > 0 && minute == 0 && second > 0){
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day == 0 && hour > 0 && minute > 0 && second > 0){
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day + ":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }
                if (day > 0 && hour == 0 && minute == 0 && second > 0 ) {
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);

                }


                if (day > 0 && hour == 0 && minute > 1 && second == 0){
                    stopTime();
                    minute--;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day > 0 && hour == 0 && minute == 1 && second == 0){
                    stopTime();
                    minute--;
                    second = 59;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day > 0 && hour > 0 && minute == 0 && second == 0){
                    stopTime();
                    hour--;
                    minute = 59;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }


                if (day > 0 && hour == 0 && minute > 0 && second > 0){
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }


                if (day > 0 && hour > 0 && minute > 0 && second == 0){
                    stopTime();
                    minute--;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day > 0 && hour > 0 && minute == 0 && second > 0){
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day+":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }

                if (day > 0 && hour > 0 && minute > 0 && second > 0){
                    stopTime();
                    second--;
                    Message message = mHandler.obtainMessage();
                    message.obj = day + ":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }
                if (day > 0 && hour == 0 && minute == 0 && second == 0){
                    stopTime();
                    day--;
                    hour = 23;
                    minute = 59;
                    second = 60;
                    Message message = mHandler.obtainMessage();
                    message.obj = day + ":"+hour + ":" + minute + ":" + second;
                    mHandler.sendMessage(message);
                }


            }

        };
        timer.schedule(task, 1000);

    }




    public void startbar(){

        Runnable myWorker = new Runnable() {
            @Override
            public void run() {
                while (status < 100) {
                    if(key == 1){
                        bar.setProgress(0);
                        i = 0;
                        key = 2;
                        break;
                    }
                    // 获取耗时操作的完成百分比
                    status = doWork();
                    // 发送消息到Handler
                    Message n = new Message();
                    n.what = 0x111;
                    // 发送消息
                    nHandler.sendMessage(n);
                }

                if (hour == 0 && minute == 0 && second == 0){
                    if (lable == 1){
                        try {

                            mediaPlayer.reset();
                            AssetManager assetManager = getAssets();
                            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("七号公园.mp3");
                            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                            mediaPlayer.prepare();
                            mediaPlayer.start();


                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(lable == 2){
                        try {
                            mediaPlayer.reset();
                            AssetManager assetManager = getAssets();
                            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("枫.mp3");
                            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    if(lable == 3){
                        try {
                            mediaPlayer.reset();
                            AssetManager assetManager = getAssets();
                            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("通关.mp3");
                            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(lable == 4){
                        try {
                            mediaPlayer.reset();
                            AssetManager assetManager = getAssets();
                            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("雅俗共赏.mp3");
                            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        BarThread = new Thread(null, myWorker, "BarThread");
        BarThread.start();

    }

    public int doWork(){
        status = i;
        i++;
        if(key == 0) {
            try {
                Thread.sleep(totaltime * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        else{
            try {
                Thread.sleep(restarttotaltime * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return status;
    }
    public void stopTime(){
        timer.cancel();
    }
    public void stop(){
        key = 1;
        restarttotaltime = 96400*day + 3600*hour + 60*minute + second;
        timer.cancel();

    }

}


