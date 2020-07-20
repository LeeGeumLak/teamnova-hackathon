package com.app.hackathon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

        addItem("내가 만든 흥나는 음악", "recorded2.mp4");
        addItem("우리 어떻게 해야 하지?", "recorded3.mp4");

        mAdapter.notifyDataSetChanged();
    }

    public void addItem(String itemTitle, String fileName) {
        MyMusicData item = new MyMusicData();

        item.setItemTitle(itemTitle);
        item.setFileName(fileName);

        mList.add(item);
    }
}
