package com.app.hackathon;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hackathon.util.AppContext;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Locale;

public class MyMusicListActivity extends AppCompatActivity {
    ImageButton backImgBtn;

    RecyclerView mRecyclerView  = null;
    MyMusicListAdapter mAdapter = null ;
    ArrayList<MyMusicData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music_list);

        backImgBtn = findViewById(R.id.backImgBtn);

        backImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.rvMusicList);

        mAdapter = new MyMusicListAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //addItem("내가 만든 흥나는 음악", "recorded2.mp4");
        //addItem("우리 어떻게 해야 하지?", "recorded3.mp4");

        String path = AppContext.getAudioOutPath().toString();
        File audioFile = new File(path);
        File[] audioFiles = audioFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase(Locale.US).endsWith(".mp3"); //확장자
            }
        });

        addItem("심심할때 만든 노래.mp3", "");
        addItem("코로나를 날릴 흥겨운 노래.mp3", "");
        addItem("옛날 노래, 흥겨운 노래.mp3", "");
        addItem("신난다아아아아아아아.mp3", "");

        for(int i = 0; i < audioFiles.length; i++) {
            addItem(audioFiles[i].getName(), audioFiles[i].getName());
        }

        mAdapter.notifyDataSetChanged();
    }

    public void addItem(String itemTitle, String fileName) {
        MyMusicData item = new MyMusicData();

        item.setItemTitle(itemTitle);
        item.setFileName(fileName);

        mList.add(item);
    }
}
