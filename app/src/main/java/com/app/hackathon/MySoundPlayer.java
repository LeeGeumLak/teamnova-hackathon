package com.app.hackathon;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class MySoundPlayer {

    public static final int HAEGEUM = R.raw.haegeum;
    public static final int KKWAENGGWARI = R.raw.kkwaenggwari;
    public static final int PIPE = R.raw.pipe;
    public static final int KOREADRUM = R.raw.korea_drum;
    public static final int AJAENG = R.raw.ajaeng;
    public static final int GAYAGEUM = R.raw.gayageum;
    public static final int HMM = R.raw.hmm;
    public static final int JANGGU = R.raw.janggu;
    public static final int SONG = R.raw.song;
    public static final int TRANSFERPIPE = R.raw.transfer_pipe;
    public static final int TRANSFERSTATION = R.raw.transfer_station;
    public static final int HEUNG = R.raw.heung;

    //오디오 리소스를 관리하고 재생하는 클래스
    private static SoundPool soundPool;
    //오디오 리소스를 담아누는 해쉬맵
    private static HashMap<Integer, Integer> soundPoolMap;

    // sound media initialize
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void initSounds(Context context) {
        /*
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();*/

        //maxStreams = 동시에 재생가능한 음원 수, streamType = 재생타입, srcQuality = 음악 재생 품질
        soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);

        //음악을 미리 넣어둠
        //initalCapacty  = 초기 용량 지정
        soundPoolMap = new HashMap(4);
        soundPoolMap.put(HAEGEUM, soundPool.load(context, HAEGEUM, 1));
        soundPoolMap.put(KKWAENGGWARI, soundPool.load(context, KKWAENGGWARI, 1));
        soundPoolMap.put(PIPE, soundPool.load(context, PIPE, 1));
        soundPoolMap.put(KOREADRUM, soundPool.load(context, KOREADRUM, 1));
        soundPoolMap.put(AJAENG, soundPool.load(context, AJAENG, 1));
        soundPoolMap.put(GAYAGEUM, soundPool.load(context, GAYAGEUM, 1));
        soundPoolMap.put(HMM, soundPool.load(context, HMM, 1));
        soundPoolMap.put(JANGGU, soundPool.load(context, JANGGU, 1));
        soundPoolMap.put(SONG, soundPool.load(context, SONG, 1));
        soundPoolMap.put(TRANSFERPIPE, soundPool.load(context, TRANSFERPIPE, 1));
        soundPoolMap.put(TRANSFERSTATION, soundPool.load(context, TRANSFERSTATION, 1));
        soundPoolMap.put(HEUNG, soundPool.load(context, HEUNG, 1));
    }

    public static void play(int raw_id){
        if( soundPoolMap.containsKey(raw_id) ) {
            //priority = 스트림 우선순위(0이 가장 낮은 우선 순위) loop = 루프모드 (0 = 루프 없음, 1 = 루프 영구)
            //rate = 재생속도 (범위:0.5 ~ 2.0)
            soundPool.play(soundPoolMap.get(raw_id), 1, 1, 1, 0, 1f);
        }
    }
}