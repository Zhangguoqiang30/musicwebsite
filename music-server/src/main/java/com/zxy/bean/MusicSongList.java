package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MusicSongList extends BaseBean {
    private Song song;
    private ListBean list;
}
