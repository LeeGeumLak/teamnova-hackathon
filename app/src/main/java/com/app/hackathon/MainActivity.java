package com.app.hackathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    String mode = "전통음악";
    String recordMode = "녹음전";
    ImageView musicIV, recordIV;
    ImageButton oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn, tenBtn, elevenBtn, twelveBtn;
    Button changeBeatBtn, recordBtn, stopRecordBtn;

    MediaRecorder recorder;
    MediaPlayer player;

    private ArrayList<TrackHolder> trackHolderList = new ArrayList<>();
    private TrackHolder recordingTrackHolder;

    private MultiAudioMixer audioMixer = MultiAudioMixer.createAudioMixer();
    private PCMAnalyser recordPcmAudioFile;
    private MLoadingDialog saveRecordedFileDialog;
    private Project project;

    // 녹음중에 클릭한 음악 파일
    private File recordingAudioFile;

    // TODO : isPlayLoop / isRecording 이벤트 처리,
    //  isRecording = true 이면 isPlayLoop = true
    private boolean isRecording = false;
    private boolean isPlayLoop = false;
    private boolean isExporting;

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
        stopRecordBtn = findViewById(R.id.stopRecordBtn);

        //화면이 처음 켜졌을 때 로딩화면을 띄운다.
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        oldMusicPicture();

        MySoundPlayer.initSounds(getApplicationContext());

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HAEGEUM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.BOOM);
                }
            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.KKWAENGGWARI);
                }else{
                    MySoundPlayer.play(MySoundPlayer.BRIDGE);
                }
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.PIPE);
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSING);
                }
            }
        });

        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.KOREADRUM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMCHIT);
                }
            }
        });

        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.AJAENG);
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMDOOM);
                }
            }
        });

        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.GAYAGEUM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.HORSE);
                }
            }
        });

        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HMM);
                }else{
                    MySoundPlayer.play(MySoundPlayer.KOONGJAK);
                }
            }
        });

        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.JANGGU);
                }else{
                    MySoundPlayer.play(MySoundPlayer.PPAXING);
                }
            }
        });

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.SONG);
                }else{
                    MySoundPlayer.play(MySoundPlayer.BEAT);
                }
            }
        });

        permissionCheck();
        final File sdcard = Environment.getExternalStorageDirectory();

        tenBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                File file = new File(sdcard, "recorded1.mp4");
                String filename = file.getAbsolutePath();
                Log.d("MainActivity", "저장할 파일 명 : " + filename);
                recordAudio(filename);
                stopRecordBtn.setVisibility(View.VISIBLE);

                return false;
            }
        });

        tenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(sdcard, "recorded1.mp4");
                String filename = file.getAbsolutePath();
                Log.d("MainActivity", "불러올 파일 명 : " + filename);
                playAudio(filename);

/*                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERPIPE);
                }else{
                    MySoundPlayer.play(MySoundPlayer.SHINDY);
                }*/
            }
        });

        elevenBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                File file = new File(sdcard, "recorded2.mp4");
                String filename = file.getAbsolutePath();
                Log.d("MainActivity", "저장할 파일 명 : " + filename);
                recordAudio(filename);
                stopRecordBtn.setVisibility(View.VISIBLE);

                return false;
            }
        });

        elevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(sdcard, "recorded2.mp4");
                String filename = file.getAbsolutePath();
                Log.d("MainActivity", "불러올 파일 명 : " + filename);
                playAudio(filename);

/*                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERSTATION);
                }else{
                    MySoundPlayer.play(MySoundPlayer.STRIONGBEAT);
                }*/
            }
        });

        twelveBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                File file = new File(sdcard, "recorded3.mp4");
                String filename = file.getAbsolutePath();
                Log.d("MainActivity", "저장할 파일 명 : " + filename);
                recordAudio(filename);
                stopRecordBtn.setVisibility(View.VISIBLE);

                return false;
            }
        });

        twelveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(sdcard, "recorded3.mp4");
                String filename = file.getAbsolutePath();
                Log.d("MainActivity", "불러올 파일 명 : " + filename);
                playAudio(filename);

/*                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HEUNG);
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSINGG);
                }*/
            }
        });

        stopRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecordBtn.setVisibility(View.INVISIBLE);
                stopRecording();
            }
        });

        // 녹음 버튼
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recordMode.equals("녹음전")){
                    recordIV.setImageResource(R.drawable.after_record);
                    recordMode = "녹음중";
                }else{
                    recordMode = "녹음전";
                    recordIV.setImageResource(R.drawable.before_record);
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
                    modernMusicPicture();
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

    // 녹음이 끝난 후,
    public void combineAudio() {
        // 녹음한 음악 저장시, dialog
        saveRecordedFileDialog = new MLoadingDialog.Builder(this)
                .title("Exporting... It could take a few minutes.")
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
    }


    // 레코드 버튼 리스너
    public void recordBtnListener() {
        if(isRecording) {
            // TODO : 분기문 추가해야함
            // 음악 클릭하면, trackholder 등록 (example)
            recordingAudioFile = new File("raw/ajaeng");
            recordingTrackHolder = new TrackHolder(recordingAudioFile);
            trackHolderList.add(recordingTrackHolder);

            // 다시 클릭하면, 해제 (example)
            recordingAudioFile = new File("raw/ajaeng");
            recordingTrackHolder = new TrackHolder(recordingAudioFile);
            for(TrackHolder trackHolder : trackHolderList) {
                if(trackHolder.equals(recordingTrackHolder)) {
                    trackHolderList.remove(trackHolder);
                }
            }
        }
        else {
            // do nothing
        }

        //onExpertAudio();
    }

    private void onExpertAudio() {
        if (!isExporting && ListUtils.isNotEmpty(trackHolderList)) {
            saveRecordedFileDialog.show();
            new ExpertThread().start();
        }
    }

    class TrackHolder implements View.OnClickListener, View.OnLongClickListener {
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
            isExporting = true;

            if (ListUtils.isEmpty(trackHolderList)) {
                return;
            }

            File[] audioFiles = new File[trackHolderList.size()];

            for (int i = 0, size = audioFiles.length; i != size; i++) {
                audioFiles[i] = trackHolderList.get(i).audioFile;
            }

            try {
                File tempMixAudioFile = new File(AppContext.getAudioTempPath(), UUID.randomUUID().toString());
                final FileOutputStream mixTempOutStream = new FileOutputStream(tempMixAudioFile);
                audioMixer.setOnAudioMixListener(new MultiAudioMixer.OnAudioMixListener() {

                    @Override
                    public void onMixing(byte[] mixBytes) throws IOException {
                        mixTempOutStream.write(mixBytes);
                    }

                    @Override
                    public void onMixError(int errorCode) {

                    }

                    @Override
                    public void onMixComplete() {

                    }
                });

                audioMixer.mixAudios(audioFiles, recordPcmAudioFile.bytesPerSample());
                mixTempOutStream.close();

                File outputFile = new File(AppContext.getAudioOutPath(), project.getName() + ".mp3");
                int channelCount = trackHolderList.size();
                AudioEncoder accEncoder = AudioEncoder.createAccEncoder(tempMixAudioFile, channelCount);
                accEncoder.encodeToFile(outputFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            isExporting = false;
        }
    }

    private void oldMusicPicture(){
        oneBtn.setBackgroundColor(getResources().getColor(R.color.traditionalPink));
        twoBtn.setBackgroundColor(getResources().getColor(R.color.traditionalPink));
        threeBtn.setBackgroundColor(getResources().getColor(R.color.traditionalPink));
        fourBtn.setBackgroundColor(getResources().getColor(R.color.traditionalBeige));
        fiveBtn.setBackgroundColor(getResources().getColor(R.color.traditionalBeige));
        sixBtn.setBackgroundColor(getResources().getColor(R.color.traditionalBeige));
        sevenBtn.setBackgroundColor(getResources().getColor(R.color.traditionalKhaki));
        eightBtn.setBackgroundColor(getResources().getColor(R.color.traditionalKhaki));
        nineBtn.setBackgroundColor(getResources().getColor(R.color.traditionalKhaki));
        tenBtn.setBackgroundColor(getResources().getColor(R.color.traditionalGreen));
        elevenBtn.setBackgroundColor(getResources().getColor(R.color.traditionalGreen));
        twelveBtn.setBackgroundColor(getResources().getColor(R.color.traditionalGreen));

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

    private void modernMusicPicture(){
        oneBtn.setBackgroundColor(getResources().getColor(R.color.modernBeige));
        twoBtn.setBackgroundColor(getResources().getColor(R.color.modernBlue));
        threeBtn.setBackgroundColor(getResources().getColor(R.color.modernPurple));
        fourBtn.setBackgroundColor(getResources().getColor(R.color.modernGreen));
        fiveBtn.setBackgroundColor(getResources().getColor(R.color.modernBlue));
        sixBtn.setBackgroundColor(getResources().getColor(R.color.modernBeige));
        sevenBtn.setBackgroundColor(getResources().getColor(R.color.modernPink));
        eightBtn.setBackgroundColor(getResources().getColor(R.color.modernGreen));
        nineBtn.setBackgroundColor(getResources().getColor(R.color.modernPurple));
        tenBtn.setBackgroundColor(getResources().getColor(R.color.modernBeige));
        elevenBtn.setBackgroundColor(getResources().getColor(R.color.modernPink));
        twelveBtn.setBackgroundColor(getResources().getColor(R.color.modernBlue));

        oneBtn.setImageResource(0);
        twoBtn.setImageResource(0);
        threeBtn.setImageResource(0);
        fourBtn.setImageResource(0);
        fiveBtn.setImageResource(0);
        sixBtn.setImageResource(0);
        sevenBtn.setImageResource(0);
        eightBtn.setImageResource(0);
        nineBtn.setImageResource(0);
        tenBtn.setImageResource(0);
        elevenBtn.setImageResource(0);
        twelveBtn.setImageResource(0);
    }

    private void recordAudio(String filename) {
        recorder = new MediaRecorder();

        /* 그대로 저장하면 용량이 크다.
         * 프레임 : 한 순간의 음성이 들어오면, 음성을 바이트 단위로 전부 저장하는 것
         * 초당 15프레임 이라면 보통 8K(8000바이트) 정도가 한순간에 저장됨
         * 따라서 용량이 크므로, 압축할 필요가 있음 */
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 어디에서 음성 데이터를 받을 것인지
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // 압축 형식 설정
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        recorder.setOutputFile(filename);

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

    private void playAudio(String filename) {
        try {
            //closePlayer();

            player = new MediaPlayer();
            player.setDataSource(filename);
            player.prepare();
            player.start();

            Toast.makeText(this, "재생 시작됨.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void permissionCheck(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
    }
}
