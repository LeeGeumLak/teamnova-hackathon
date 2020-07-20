package com.app.hackathon;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hackathon.util.AppContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyMusicListAdapter extends RecyclerView.Adapter<MyMusicListAdapter.ViewHolder> {
    private ArrayList<MyMusicData> mData = null;
    private Context mContext;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MyMusicListAdapter(ArrayList<MyMusicData> list) {
        mData = list;
    }

    public MyMusicListAdapter(Context context, ArrayList<MyMusicData> myMusicDataList){
        this.mData = myMusicDataList;
        mContext = context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public MyMusicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        View view = inflater.inflate(R.layout.item_my_music, parent, false);
        MyMusicListAdapter.ViewHolder vh = new MyMusicListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MyMusicListAdapter.ViewHolder holder, int position) {
        MyMusicData item = mData.get(position);

        holder.tvMusicName.setText(item.getItemTitle());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton playImgBtn;
        TextView tvMusicName;

        MediaPlayer player;
        private int position = 0;

        String playMode = "재생전";

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            playImgBtn = itemView.findViewById(R.id.playImgBtn) ;
            tvMusicName = itemView.findViewById(R.id.tvMusicName) ;

            //playImgBtn.setImageResource(R.drawable.play);

            final File sdcard = AppContext.getAudioOutPath();
            playImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(playMode.equals("재생전")){
                        int pos = getAdapterPosition();

                        playImgBtn.setImageResource(R.drawable.pause);

                        if (pos != RecyclerView.NO_POSITION) {
                            MyMusicData myMusic = mData.get(pos);

                            String selectedMusic = myMusic.getFileName();
                            File file = new File(sdcard, selectedMusic);
                            String filename = file.getAbsolutePath();
                            Log.d("MyMusicListAdapter", "불러올 파일 명 : " + filename);
                            playAudio(filename);
                        }

                        playMode = "재생중";
                    }else{
                        playMode = "재생전";
                        //playImgBtn.setImageResource(R.drawable.play);
                    }

                }
            });
        }

        // 저장된 오디오 재생하는 코드
        private void playAudio(String filename) {
            try {
                player = new MediaPlayer();
                player.setDataSource(filename);
                player.prepare();
                player.start();
                Toast.makeText(mContext, "재생 시작됨.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void pauseAudio() {
            if (player != null) {
                position = player.getCurrentPosition();
                player.pause();

                Toast.makeText(mContext, "일시정지됨.", Toast.LENGTH_SHORT).show();
            }
        }

        private void resumeAudio() {
            if (player != null && !player.isPlaying()) {
                player.seekTo(position);
                player.start();

                Toast.makeText(mContext, "재시작됨.", Toast.LENGTH_SHORT).show();
            }
        }

        private void stopAudio() {
            if (player != null && player.isPlaying()) {
                player.stop();

                Toast.makeText(mContext, "중지됨.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}