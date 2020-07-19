package com.app.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class LoopStationActivity extends AppCompatActivity implements View.OnClickListener {

    // 재생 버튼이 눌렸는지 안눌렸는지 판단하는 변수
    private int playButtonPressed = 0;

    private MediaRecorder recorder;
    private String recordedFileName;

    private MediaPlayer player;

    // 현재 재생 위치 확인 변수
    private int playPosition = 0;

    // 현재 녹음줃인지 확인 변수
    private boolean isRecording = false;

    // 녹음 파일 재생 버튼
    private Button buttonPlay, buttonStop;
    private TextView loopStationContent_1, loopStationContent_2, loopStationContent_3,
            loopStationContent_4, loopStationContent_5, loopStationContent_6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_station);

        initViewId();
    }

    public void initViewId() {
        // 녹음 파일 재생/정지 버튼
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonStop = findViewById(R.id.buttonStop);

        // 녹음된 파일 노출 공간
        loopStationContent_1 = findViewById(R.id.loopStationContent_1);
        loopStationContent_2 = findViewById(R.id.loopStationContent_2);
        loopStationContent_3 = findViewById(R.id.loopStationContent_3);
        loopStationContent_4 = findViewById(R.id.loopStationContent_4);
        loopStationContent_5 = findViewById(R.id.loopStationContent_5);
        loopStationContent_6 = findViewById(R.id.loopStationContent_6);

        // 플레이 버튼 클릭시, 일시정지 이미지로 변경 (그 반대 경우도)
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playButtonPressed == 0) {
                    buttonPlay.setBackgroundResource(R.drawable.pause);
                    playButtonPressed = 1;

                    playAudio();
                }
                else {
                    buttonPlay.setBackgroundResource(R.drawable.play);
                    playButtonPressed = 0;

                    pauseAudio();
                }
            }
        });


        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAudio();
            }
        });

        loopStationContent_1.setOnClickListener(this);
        loopStationContent_2.setOnClickListener(this);
        loopStationContent_3.setOnClickListener(this);
        loopStationContent_4.setOnClickListener(this);
        loopStationContent_5.setOnClickListener(this);
        loopStationContent_6.setOnClickListener(this);
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loopStationContent_1:
            case R.id.loopStationContent_2:
            case R.id.loopStationContent_3:
            case R.id.loopStationContent_4:
            case R.id.loopStationContent_5:
            case R.id.loopStationContent_6:

                permissionCheck();

                // 녹음중이지 않을때,
                if(!isRecording) {
                    recordAudio();
                }
                // 녹음중일때,
                else {
                    stopRecording();

/*                    // 파일 저장할 폴더 생성
                    String folderName = "loopStation";
                    File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folderName);

                    // 현재 시간/날짜로 mp4 파일 생성
                    long currentTime = System.currentTimeMillis();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");

                    Date currentDate = new Date(currentTime);
                    String currentDateTime = dateFormat.format(currentDate);

                    String folderPath = folder.getAbsolutePath();
                    File file = new File(folderPath + File.separator + currentDateTime + ".mp3");
                    recordedFileName = file.getAbsolutePath();*/

                    File sdcard = Environment.getExternalStorageDirectory();
                    File file = new File(sdcard, "recorded.mp4");
                    recordedFileName = file.getAbsolutePath();

                    Log.d("MainActivity", "파일 명 : " + recordedFileName);
                }

            default:
                // do nothing
                Log.d("MainActivity", "루프스테이션 content 외 클릭");
        }
    }

    private void recordAudio() {
        recorder = new MediaRecorder();

        /* 그대로 저장하면 용량이 크다.
         * 프레임 : 한 순간의 음성이 들어오면, 음성을 바이트 단위로 전부 저장하는 것
         * 초당 15프레임 이라면 보통 8K(8000바이트) 정도가 한순간에 저장됨
         * 따라서 용량이 크므로, 압축할 필요가 있음 */
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 어디에서 음성 데이터를 받을 것인지
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 압축 형식 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(recordedFileName);

        try {
            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "녹음 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(this, "녹음 중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio() {
        try {
            closePlayer();

            player = new MediaPlayer();
            player.setDataSource(recordedFileName);
            player.prepare();
            player.start();

            Toast.makeText(this, "재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseAudio() {
        if (player != null) {
            playPosition = player.getCurrentPosition();
            player.pause();

            Toast.makeText(this, "일시정지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudio() {
        if (player != null && player.isPlaying()) {
            player.stop();

            Toast.makeText(this, "중지됨.", Toast.LENGTH_SHORT).show();
        }
    }

    public void closePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

}
