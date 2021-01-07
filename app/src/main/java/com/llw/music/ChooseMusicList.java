package com.llw.music;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.llw.music.adapter.ChooseMusicListAdapter;
import com.llw.music.adapter.MusicListAdapter;
import com.llw.music.model.Song;
import com.llw.music.utils.MusicUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChooseMusicList extends Fragment implements MediaPlayer.OnCompletionListener{

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();
    private ChooseMusicListAdapter Adapter;//歌曲适配器
    private List<Song> mList;
    private RecyclerView listView;
    private LinearLayout musicList;
    private TextView titleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.choose_music_list,container,false);
        listView = (RecyclerView) view.findViewById(R.id.list_view);
        musicList = (LinearLayout) view.findViewById(R.id.music_list);
        titleView = (TextView) view.findViewById(R.id.title);
        mList = new ArrayList<>();//实例化
        //数据赋值
        mList = MusicUtils.getMusicData(MyApplication.getContext());//将扫描到的音乐赋值给音乐列表
        Adapter = new ChooseMusicListAdapter(R.layout.choose_music, mList);//指定适配器的布局和数据源
        listView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        listView.setAdapter(Adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyApplication.getContext(),"you click this title",Toast.LENGTH_SHORT).show();
            }
        });

        Adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.music_list){
                    Toast.makeText(MyApplication.getContext(),"you choose this item",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
