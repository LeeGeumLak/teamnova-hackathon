package com.app.hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.hackathon.dialog.MLoadingDialog;
import com.app.hackathon.util.AppContext;
import com.app.hackathon.util.AudioEncoder;
import com.app.hackathon.util.ListUtils;
import com.app.hackathon.util.MultiAudioMixer;
import com.app.hackathon.util.PCMAnalyser;
import com.app.hackathon.util.Project;
import com.app.hackathon.util.Track;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity {

    String mode = "전통음악";
    String recordMode = "녹음전";
    ImageView musicIV, recordIV;
    ImageButton oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn, tenBtn, elevenBtn, twelveBtn;
    Button changeBeatBtn, recordBtn;


    private ArrayList<TrackHolder> trackHolderList = new ArrayList<>();
    private TrackHolder recordingTrackHolder;

    private MultiAudioMixer audioMixer = MultiAudioMixer.createAudioMixer();
    private PCMAnalyser recordPcmAudioFile;
    private MLoadingDialog saveRecordedFileDialog;
    private Project project;
    private File myDir;

    // 녹음중에 클릭한 음악 파일
    private File recordingAudioFile;

    private boolean isRecording = false;
    //private boolean isPlayLoop = false;
    private boolean isExporting = false;

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
        musicIV = findViewById(R.id.musicImage);
        recordBtn = findViewById(R.id.recordBtn);
        recordIV = findViewById(R.id.recordImage);

        //화면이 처음 켜졌을 때 로딩화면을 띄운다.
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        requestPermission();

        oldMusicPicture();

        MySoundPlayer.initSounds(getApplicationContext());

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HAEGEUM);
                    recordBtnListener("raw/haegeum");
                }else{
                    MySoundPlayer.play(MySoundPlayer.BOOM);
                    recordBtnListener("raw/boom");
                }
            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.KKWAENGGWARI);
                    recordBtnListener("raw/kkwaenggwari");
                }else{
                    MySoundPlayer.play(MySoundPlayer.BRIDGE);
                    recordBtnListener("raw/bridge");
                }
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.PIPE);
                    recordBtnListener("raw/pipe");
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSING);
                    recordBtnListener("raw/closing");
                }
            }
        });

        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.KOREADRUM);
                    recordBtnListener("raw/korea_drum");
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMCHIT);
                    recordBtnListener("raw/doom_chit");
                }
            }
        });

        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.AJAENG);
                    recordBtnListener("raw/ajaeng");
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMDOOM);
                    recordBtnListener("raw/doomdoom");
                }
            }
        });

        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.GAYAGEUM);
                    recordBtnListener("raw/gayageum");
                }else{
                    MySoundPlayer.play(MySoundPlayer.HORSE);
                    recordBtnListener("raw/horse");
                }
            }
        });

        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HMM);
                    recordBtnListener("raw/hmm");
                }else{
                    MySoundPlayer.play(MySoundPlayer.KOONGJAK);
                    recordBtnListener("raw/koong_jak");
                }
            }
        });

        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.JANGGU);
                    recordBtnListener("raw/janggu");
                }else{
                    MySoundPlayer.play(MySoundPlayer.PPAXING);
                    recordBtnListener("raw/ppaxing");
                }
            }
        });

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.SONG);
                    recordBtnListener("raw/song");
                }else{
                    MySoundPlayer.play(MySoundPlayer.BEAT);
                    recordBtnListener("raw/right_beat");
                }
            }
        });

        tenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERPIPE);
                    recordBtnListener("raw/transfer_pipe");
                }else{
                    MySoundPlayer.play(MySoundPlayer.SHINDY);
                    recordBtnListener("raw/shindy");
                }
            }
        });

        elevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERSTATION);
                    recordBtnListener("raw/transfer_station");
                }else{
                    MySoundPlayer.play(MySoundPlayer.STRIONGBEAT);
                    recordBtnListener("raw/strong_beat");
                }
            }
        });

        twelveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HEUNG);
                    recordBtnListener("raw/heung");
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSINGG);
                    recordBtnListener("raw/closing");
                }
            }
        });

        // 녹음 버튼
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordMode.equals("녹음전")){
                    recordIV.setImageResource(R.drawable.after_record);
                    recordMode = "녹음중";

                    isRecording = true;
                    //isPlayLoop = true;

                }else{
                    recordMode = "녹음전";
                    recordIV.setImageResource(R.drawable.before_record);

                    isRecording = false;
                    //isPlayLoop = false;

                    onExpertAudio();
                }

                Log.d("MainActivity", recordMode);
            }
        });

        // 현대/전통 음악 버튼
        changeBeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    changeBeatBtn.setText("현대음악");
                    musicIV.setImageResource(R.drawable.img_music);
                    mode = "현대음악";
                }else{
                    mode = "전통음악";
                    changeBeatBtn.setText("전통음악");
                    oldMusicPicture();
                    musicIV.setImageResource(R.drawable.img_jungganbo);
                }

                Log.d("MainActivity", mode);
            }
        });
    }

    private void requestPermission() {
        boolean shouldProviceRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);//사용자가 이전에 거절한적이 있어도 true 반환

        if (shouldProviceRationale) {
            //앱에 필요한 권한이 없어서 권한 요청
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            //권한있을때.
            //오레오부터 꼭 권한체크내에서 파일 만들어줘야함
            makeDir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 허용 선택시
                    //오레오부터 꼭 권한체크내에서 파일 만들어줘야함
                    makeDir();
                } else {
                    //사용자가 권한 거절시
                    denialDialog();
                }
                return;
            }
        }
    }

    public void denialDialog() {
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("저장소 권한이 필요합니다. 환경 설정에서 저장소 권한을 허가해주세요.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent); //확인버튼누르면 바로 어플리케이션 권한 설정 창으로 이동하도록
                    }
                })
                .create()
                .show();
    }

    public void makeDir() {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath(); //내장에 만든다
        String directoryName = "HeungMaker";
        myDir = new File(root + "/" + directoryName);
        if (!myDir.exists()) {
            boolean wasSuccessful = myDir.mkdir();
            if (!wasSuccessful) {
                System.out.println("file: was not successful.");
            } else {
                System.out.println("file: 최초로 앨범파일만듬." + root + "/" + directoryName);
            }
        } else {
            System.out.println("file: " + root + "/" + directoryName +"already exists");
        }
    }

    // 레코드 버튼 리스너
    public void recordBtnListener(String filePathName) {
        boolean isRemoved = false;

        if(isRecording) {
            Log.d("MainActivity", "녹음중");

            recordingAudioFile = new File(filePathName);
            recordingTrackHolder = new TrackHolder(recordingAudioFile);

            Log.d("MainActivity", "파일 생성 및 tracker holder에 넣음");

            for(TrackHolder trackHolder : trackHolderList) {

                Log.d("MainActivity", "for문 탐색");

                if(trackHolder.equals(recordingTrackHolder)) {
                    Log.d("MainActivity", "같은거 발견, 삭제");
                    trackHolderList.remove(trackHolder);
                    isRemoved = true;
                    break;
                }
            }

            if(!isRemoved) {
                Log.d("MainActivity", "같은거 없어서 추가");

                trackHolderList.add(recordingTrackHolder);
            }
        }
        else {
            Log.d("MainActivity", "녹음중 아님");
            // do nothing
        }
    }

    private void onExpertAudio() {
        if (!isExporting && ListUtils.isNotEmpty(trackHolderList)) {
            Log.d("MainActivity", "onExpertAudio go");

            isExporting = true;
            //saveRecordedFileDialog.show();
            new ExpertThread().start();
        }
        else {
            Log.d("MainActivity", "onExpertAudio not go");
        }
    }

    static class TrackHolder implements View.OnClickListener, View.OnLongClickListener {
        Track track;
        File audioFile;
        List<Double> audioFrames = new ArrayList<>();
        InputStream audioStream;

        TrackHolder() { }

        TrackHolder(File audioFile) {
            this.audioFile = audioFile;
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }

    private class ExpertThread extends Thread {
        @Override
        public void run() {
            Log.d("MainActivity", "expert thread start");

            if (ListUtils.isEmpty(trackHolderList)) {
                Log.d("MainActivity", "list empty");

                isExporting = false;
                return;
            }

            File[] audioFiles = new File[trackHolderList.size()];

            for (int i = 0, size = audioFiles.length; i != size; i++) {
                audioFiles[i] = trackHolderList.get(i).audioFile;
            }

            try {
                Log.d("MainActivity", "try mix");

                Log.d("MainActivity", "임시 파일 생성");

                File tempMixAudioFile = new File(AppContext.getAudioTempPath(), UUID.randomUUID().toString());
                Log.d("MainActivity", tempMixAudioFile.toString());

                // TODO : 시스템 에러 발생
                final FileOutputStream mixTempOutStream = new FileOutputStream(tempMixAudioFile);

                Log.d("MainActivity", "임시파일 output stream에 입력");

                audioMixer.setOnAudioMixListener(new MultiAudioMixer.OnAudioMixListener() {
                    @Override
                    public void onMixing(byte[] mixBytes) throws IOException {
                        mixTempOutStream.write(mixBytes);
                        Log.d("MainActivity", "onMixing");
                    }

                    @Override
                    public void onMixError(int errorCode) {
                        Log.d("MainActivity", "onMixError");
                    }

                    @Override
                    public void onMixComplete() {
                        Log.d("MainActivity", "onMixComplete");

                    }
                });

                audioMixer.mixAudios(audioFiles, recordPcmAudioFile.bytesPerSample());
                Log.d("MainActivity", "audioMixer");

                mixTempOutStream.close();

                File outputFile = new File(AppContext.getAudioOutPath(), project.getName() + "1.mp3");
                int channelCount = trackHolderList.size();
                AudioEncoder accEncoder = AudioEncoder.createAccEncoder(tempMixAudioFile, channelCount);
                accEncoder.encodeToFile(outputFile);

                Log.d("MainActivity", "file encode and save");

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            isExporting = false;
        }
    }

    private void oldMusicPicture(){
        oneBtn.setImageResource(R.drawable.imgbtn_bibimbap);
        twoBtn.setImageResource(R.drawable.imgbtn_building);
        threeBtn.setImageResource(R.drawable.imgbtn_duck);
        fourBtn.setImageResource(R.drawable.imgbtn_fan);
        fiveBtn.setImageResource(R.drawable.imgbtn_ginseng);
        sixBtn.setImageResource(R.drawable.imgbtn_gyeongbokgung_palace);
        sevenBtn.setImageResource(R.drawable.imgbtn_harubang);
        eightBtn.setImageResource(R.drawable.imgbtn_mask);
        nineBtn.setImageResource(R.drawable.imgbtn_pagoda);
        tenBtn.setImageResource(R.drawable.imgbtn_seoraksan);
        elevenBtn.setImageResource(R.drawable.imgbtn_seoul_tower);
        twelveBtn.setImageResource(R.drawable.imgbtn_shoes);
    }
}
