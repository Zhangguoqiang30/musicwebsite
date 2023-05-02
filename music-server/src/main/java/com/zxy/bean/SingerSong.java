package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SingerSong extends BaseBean {
    private Singer singer;
    private Song song;
}
