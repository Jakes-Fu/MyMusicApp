package com.llw.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.llw.music.adapter.ChooseMusicListAdapter;
import com.llw.music.model.Song;
import com.llw.music.utils.MusicUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.llw.music.utils.DateUtil.parseTime;


public class MusicListActivity extends Fragment implements MediaPlayer.OnCompletionListener {

    private static final int INTERNAL_TIME = 1000;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private ChooseMusicListAdapter Adapter;//歌曲适配器
    private List<Song> mList;
    private RecyclerView listView;
    private LinearLayout musicList;
    private TextView titleView;
    private LinearLayout chooseMusicList;

    public int mCurrentPosition;
    private MediaPlayer mediaPlayer;//音频播放器
    private TextView tvPlaySongInfo;
    private ImageView albumImg;
    private LinearLayout playStateLay;
    private SeekBar timeSeekBar;
    private TextView tvTotalTime;
    private ImageView playStateImg;
    private ImageView btnPlayOrPause;
    private TextView tvPlayTime;
    private DrawerLayout chooseMusic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_music_item, container, false);

        listView = (RecyclerView) view.findViewById(R.id.list_view);
        musicList = (LinearLayout) view.findViewById(R.id.music_list_item);
        chooseMusicList = (LinearLayout) view.findViewById(R.id.choose_music_list);
        titleView = (TextView) view.findViewById(R.id.list_title);
        chooseMusic = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        initMusic();
//        mList = new ArrayList<>();//实例化
//        //数据赋值
//        mList = MusicUtils.getMusicData(MyApplication.getContext());//将扫描到的音乐赋值给播放列表
//        Adapter = new ChooseMusicListAdapter(R.layout.choose_music, mList);//指定适配器的布局和数据源
//        listView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
//        listView.setAdapter(Adapter);

        return view;
    }

    private void initMusic(){
        mList = new ArrayList<>();//实例化
        //数据赋值
        mList = MusicUtils.getMusicData(MyApplication.getContext());//将扫描到的音乐赋值给播放列表
        Adapter = new ChooseMusicListAdapter(R.layout.choose_music, mList);//指定适配器的布局和数据源
        listView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        listView.setAdapter(Adapter);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(), "you click this title", Toast.LENGTH_SHORT).show();
            }
        });

        Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.music_list_item){
                    mCurrentPosition = position;
                    changeMusic(mCurrentPosition);
                }
            }
        });

    }
    //切歌
    private void changeMusic(int position) {

        Log.e("MainActivity", "position:" + position);
        if (position < 0) {
            mCurrentPosition = position = mList.size() - 1;
            Log.e("MainActivity", "mList.size:" + mList.size());
        } else if (position > mList.size() - 1) {
            mCurrentPosition = position = 0;
        }
        Log.e("MainActivity", "position:" + position);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);//监听音乐播放完毕事件，自动下一曲
        }

        try {
            // 切歌之前先重置，释放掉之前的资源
            mediaPlayer.reset();
            // 设置播放源
            Log.d("Music", mList.get(position).path);
            mediaPlayer.setDataSource(mList.get(position).path);

            tvPlaySongInfo.setText("歌名： " + mList.get(position).song + "   歌手： " + mList.get(position).singer);


            //使用ImageBitmap来加载专辑图片
            albumImg.setImageBitmap(MusicUtils.getAlbumPicture(MyApplication.getContext(), mList.get(position).getPath()));

            //使用Glide动态加载初始化专辑图片
//            Glide.with(this).load(R.mipmap.icon_empty).into(albumImg);
            tvPlaySongInfo.setSelected(true);//跑马灯效果
            playStateLay.setVisibility(View.VISIBLE);

            // 开始播放前的准备工作，加载多媒体资源，获取相关信息
            mediaPlayer.prepare();
            // 开始播放
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 切歌时重置进度条并展示歌曲时长
        timeSeekBar.setProgress(0);
        timeSeekBar.setMax(mediaPlayer.getDuration());
        tvTotalTime.setText(parseTime(mediaPlayer.getDuration()));

        updateProgress();
        if (mediaPlayer.isPlaying()) {
            btnPlayOrPause.setBackground(getResources().getDrawable(R.mipmap.icon_play));
            playStateImg.setBackground(getResources().getDrawable(R.mipmap.list_pause_state));
        } else {
            btnPlayOrPause.setBackground(getResources().getDrawable(R.mipmap.icon_pause));
            playStateImg.setBackground(getResources().getDrawable(R.mipmap.list_play_state));
        }
    }
    private Handler mHandler = new Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            // 展示给进度条和当前时间
            int progress = mediaPlayer.getCurrentPosition();
            timeSeekBar.setProgress(progress);
            tvPlayTime.setText(parseTime(progress));
            // 继续定时发送数据
            updateProgress();
            return true;
        }
    });

    private void updateProgress() {
        // 使用Handler每间隔1s发送一次空消息，通知进度条更新
        Message msg = Message.obtain();// 获取一个现成的消息
        // 使用MediaPlayer获取当前播放时间除以总时间的进度
        int progress = mediaPlayer.getCurrentPosition();
        msg.arg1 = progress;
        mHandler.sendMessageDelayed(msg,INTERNAL_TIME);
    }

    //滑动条监听
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        // 当手停止拖拽进度条时执行该方法
        // 获取拖拽进度
        // 将进度对应设置给MediaPlayer
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            mediaPlayer.seekTo(progress);

        }
    };



    @Override
    public void onCompletion(MediaPlayer mp) {
        changeMusic(++mCurrentPosition);
    }
}
