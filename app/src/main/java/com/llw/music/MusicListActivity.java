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

    public static final String TAG="LeftFragment";
    private static final int INTERNAL_TIME = 1000;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private ChooseMusicListAdapter Adapter;//歌曲适配器
    private List<Song> mList;
    private RecyclerView listView;
    private LinearLayout musicList;
    private TextView titleView;
    private LinearLayout chooseMusicList;
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
        mList = new ArrayList<>();//实例化
        //数据赋值
        mList = MusicUtils.getMusicData(MyApplication.getContext());//将扫描到的音乐赋值给播放列表
        Adapter = new ChooseMusicListAdapter(R.layout.choose_music, mList);//指定适配器的布局和数据源
        listView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        listView.setAdapter(Adapter);

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

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
