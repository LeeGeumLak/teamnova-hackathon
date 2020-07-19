package com.app.hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String mode = "옛노래";
    Button oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn, tenBtn, elevenBtn, twelveBtn;
    Button changeBeatBtn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oneBtn = findViewById(R.id.one);
        twoBtn = findViewById(R.id.two);
        threeBtn = findViewById(R.id.three);
        fourBtn = findViewById(R.id.four);
        fiveBtn = findViewById(R.id.five);
        sixBtn = findViewById(R.id.six);
        sevenBtn = findViewById(R.id.seven);
        eightBtn = findViewById(R.id.eight);
        nineBtn = findViewById(R.id.nine);
        tenBtn = findViewById(R.id.ten);
        elevenBtn = findViewById(R.id.eleven);
        twelveBtn = findViewById(R.id.twelve);
        changeBeatBtn = findViewById(R.id.changeBeat);

        //화면이 처음 켜졌을 때 로딩화면을 띄운다.
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        MySoundPlayer.initSounds(getApplicationContext());

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.HAEGEUM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.BOOM);
                }
            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.KKWAENGGWARI);
                }else{
                    MySoundPlayer.play(MySoundPlayer.BRIDGE);
                }
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.PIPE);
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSING);
                }
            }
        });

        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.KOREADRUM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMCHIT);
                }
            }
        });

        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.AJAENG);
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMDOOM);
                }
            }
        });

        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.GAYAGEUM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.HORSE);
                }
            }
        });

        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.HMM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.KOONGJAK);
                }
            }
        });

        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.JANGGU);
                }else{
                    MySoundPlayer.play(MySoundPlayer.PPAXING);
                }
            }
        });

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.SONG);
                }else{
                    MySoundPlayer.play(MySoundPlayer.BEAT);
                }
            }
        });

        tenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERPIPE);
                }else{
                    MySoundPlayer.play(MySoundPlayer.SHINDY);
                }
            }
        });

        elevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERSTATION);
                }else{
                    MySoundPlayer.play(MySoundPlayer.STRIONGBEAT);
                }
            }
        });

        twelveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    MySoundPlayer.play(MySoundPlayer.HEUNG);
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSINGG);
                }
            }
        });

        changeBeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("옛노래")){
                    mode = "현대노래";
                }else{
                    mode = "옛노래";
                }
                changeBeatBtn.setText(mode);
                Log.d("MainActivity", mode);
            }
        });
    }
}
