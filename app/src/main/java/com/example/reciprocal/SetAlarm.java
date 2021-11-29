package com.example.reciprocal;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class SetAlarm extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Button qihao,feng,tongguan,yasu,ok;
    private EditText tab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        initView();

    }
    private void initView() {
        qihao = findViewById(R.id.qihao);
        feng = findViewById(R.id.feng);
        tongguan = findViewById(R.id.tongguan);
        yasu = findViewById(R.id.yasu);
        ok = findViewById(R.id.ok);
        tab = findViewById(R.id.tab);
        qihao.setOnClickListener(this);
        feng.setOnClickListener(this);
        tongguan.setOnClickListener(this);
        yasu.setOnClickListener(this);
        ok.setOnClickListener(this);
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qihao:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                else {
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
                break;
            case R.id.feng:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                else {
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
                break;

            case R.id.tongguan:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }

                else {
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

                break;

            case R.id.yasu:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }

                else {
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

                break;

            case R.id.ok:

                String content = tab.getText().toString();
                Toast toast1=Toast.makeText(SetAlarm.this, "选择不能为空", Toast.LENGTH_SHORT);
                Toast toast2=Toast.makeText(SetAlarm.this, "输入有误，请重新选择", Toast.LENGTH_SHORT);
                if(content.isEmpty()){
                    toast1.show();
                }
                else if ( Integer.parseInt(tab.getText().toString())!=1 && Integer.parseInt(tab.getText().toString())!=2 && Integer.parseInt(tab.getText().toString())!=3&& Integer.parseInt(tab.getText().toString())!=4 ){
                    toast2.show();
                }

                else {
                    Intent intent = new Intent(SetAlarm.this, MainActivity.class);
                    intent.putExtra("lable", Integer.parseInt(tab.getText().toString()));
                    startActivity(intent);
                    finish();
                }
                break;

            default:
                break;


        }

    }

}