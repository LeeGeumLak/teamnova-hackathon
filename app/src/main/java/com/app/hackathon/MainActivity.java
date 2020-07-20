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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.hackathon.util.AppContext;
import com.app.hackathon.util.MultiAudioMixer;
import com.app.hackathon.util.PCMAnalyser;
import com.app.hackathon.util.Project;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String mode = "전통음악";
    String recordMode = "녹음전";
    ImageView musicIV, recordIV;
    ImageButton oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn, tenBtn, elevenBtn, twelveBtn;
    ImageButton stopRecordBtn;
    Button changeBeatBtn, recordBtn;

    MediaRecorder recorder;
    MediaPlayer player;

    //private ArrayList<TrackHolder> trackHolderList = new ArrayList<>();
    //private TrackHolder recordingTrackHolder;

    /*private MultiAudioMixer audioMixer = MultiAudioMixer.createAudioMixer();
    private PCMAnalyser recordPcmAudioFile = PCMAnalyser.createPCMAnalyser();
    private Project project;*/

    // 녹음중에 클릭한 음악 파일
    //private File recordingAudioFile;

    //private boolean isRecording = false;
    //private boolean isPlayLoop = false;
    //private boolean isExporting;

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
                    //recordBtnListener("raw/haegeum");
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSINGG);
                    //recordBtnListener("raw/boom");
                }
            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.KKWAENGGWARI);
                    //recordBtnListener("raw/kkwaenggwari");
                }else{
                    MySoundPlayer.play(MySoundPlayer.STRIONGBEAT);
                    //recordBtnListener("raw/bridge");
                }
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HEUNG);
                    //recordBtnListener("raw/pipe");
                }else{
                    MySoundPlayer.play(MySoundPlayer.CLOSING);
                    //recordBtnListener("raw/closing");
                }
            }
        });

        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.KOREADRUM);
                    //recordBtnListener("raw/korea_drum");
                }else{
                    MySoundPlayer.play(MySoundPlayer.DOOMCHIT);
                    //recordBtnListener("raw/doom_chit");
                }
            }
        });

        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.AJAENG);
                    //recordBtnListener("raw/ajaeng");
                }else{
                    MySoundPlayer.play(MySoundPlayer.SHINDY);
                    //recordBtnListener("raw/doomdoom");
                }
            }
        });

        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERSTATION);
                    //recordBtnListener("raw/gayageum");
                }else{
                    MySoundPlayer.play(MySoundPlayer.HORSE);
                    //recordBtnListener("raw/horse");
                }
            }
        });

        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.HMM);
                    //recordBtnListener("raw/hmm");
                }else{
                    MySoundPlayer.play(MySoundPlayer.KOONGJAK);
                    //recordBtnListener("raw/koong_jak");
                }
            }
        });

        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.TRANSFERPIPE);
                    //recordBtnListener("raw/janggu");
                }else{
                    MySoundPlayer.play(MySoundPlayer.PPAXING);
                    //recordBtnListener("raw/ppaxing");
                }
            }
        });

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("전통음악")){
                    MySoundPlayer.play(MySoundPlayer.SONG);
                    //recordBtnListener("raw/song");
                }else{
                    MySoundPlayer.play(MySoundPlayer.BEAT);
                    //recordBtnListener("raw/right_beat");
                }
            }
        });

        permissionCheck();
        final File sdcard = AppContext.getAudioTempPath();

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

                    //isRecording = true;
                    //isPlayLoop = true;

                    // 찍은 시간에 따른 파일 이름 설정
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(new Date());

                    File output = AppContext.getAudioOutPath();
                    File file = new File(output, "Output_" + timeStamp + ".mp3");
                    String filename = file.getAbsolutePath();
                    Log.d("MainActivity", "저장할 파일 명 : " + filename);
                    recordAudio(filename);
                }else{
                    recordMode = "녹음전";
                    recordIV.setImageResource(R.drawable.before_record);

                    //isRecording = false;
                    //isPlayLoop = false;

                    //onExpertAudio();

                    stopRecording();
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
    /*public void combineAudio() {
        // 녹음한 음악 저장시, dialog
        saveRecordedFileDialog = new MLoadingDialog.Builder(this)
                .title("Exporting... It could take a few minutes.")
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
    }*/

    /*// 레코드 버튼 리스너
    public void recordBtnListener(String filePathName) {
        if(isRecording) {
            Log.d("MainActivity", "녹음중");

            recordingAudioFile = new File(filePathName);
            recordingTrackHolder = new TrackHolder(recordingAudioFile);
            trackHolderList.add(0, recordingTrackHolder);
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

                permissionCheck();
                File tempMixAudioDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HeungMaker" + File.separator + "Audio");

                if(!tempMixAudioDirectory.exists()) {
                    boolean isDirectoryCreated = tempMixAudioDirectory.mkdirs();
                    Log.d("MainActivity", "audio 디렉토리 생성 성공/실패 : " + isDirectoryCreated);
                }

                File tempMixAudioFile = new File(tempMixAudioDirectory, UUID.randomUUID().toString());

                boolean isFileCreated = tempMixAudioFile.createNewFile();

                Log.d("MainActivity", tempMixAudioFile.toString());

                if(isFileCreated) {
                    Log.d("MainActivity", "audio 파일 생성 성공");

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

                    // TODO : 시스템 에러
                    audioMixer.mixAudios(audioFiles, recordPcmAudioFile.bytesPerSample());
                    Log.d("MainActivity", "audioMixer");

                    mixTempOutStream.close();

                    File outputDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HeungMaker" + File.separator + "Output");
                    if(!outputDirectory.exists()) {
                        boolean isDirectoryCreated = outputDirectory.mkdirs();
                        Log.d("MainActivity", "output 디렉토리 생성 성공/실패 : " + isDirectoryCreated);
                    }

                    // 찍은 시간에 따른 파일 이름 설정
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(new Date());

                    File outputFile = new File(outputDirectory, "Output_" + timeStamp + ".mp3");
                    isFileCreated = outputFile.createNewFile();

                    if(isFileCreated) {
                        Log.d("MainActivity", "output 파일 생성 성공");

                        int channelCount = trackHolderList.size();
                        AudioEncoder accEncoder = AudioEncoder.createAccEncoder(tempMixAudioFile, channelCount);
                        accEncoder.encodeToFile(outputFile);

                        Log.d("MainActivity", "file encode and save");
                    }
                    else {
                        Log.d("MainActivity", "output 파일 생성 실패");
                    }
                }
                else {
                    Log.d("MainActivity", "파일 생성 실패");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            isExporting = false;
        }
    }*/

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