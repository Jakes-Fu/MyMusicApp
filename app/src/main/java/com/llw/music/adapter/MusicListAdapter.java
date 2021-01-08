package com.llw.music.adapter;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.llw.music.R;
import com.llw.music.model.Song;
import com.llw.music.utils.MusicUtils;

import java.util.List;

public class MusicListAdapter extends BaseQuickAdapter<Song, BaseViewHolder>{

    public MusicListAdapter(int layoutResId, @Nullable List<Song> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Song item) {
        //给控件赋值
        int duration = item.duration;
        String time = MusicUtils.formatTime(duration);

        helper.setText(R.id.tv_song_name,item.getSong().trim())//歌曲名称
                .setText(R.id.tv_singer,item.getSinger()+" - "+item.getAlbum())//歌手 - 专辑
                .setText(R.id.tv_duration_time,time)//歌曲时间
                //歌曲序号，因为getAdapterPosition得到的位置是从0开始，故而加1，
                //是因为位置和1都是整数类型，直接赋值给TextView会报错，故而拼接了""
                .setText(R.id.tv_position,helper.getAdapterPosition()+1+"");
        helper.addOnClickListener(R.id.item_music);//给item添加点击事件，点击之后传递数据到播放页面或者在本页面进行音乐播放

    }
}
