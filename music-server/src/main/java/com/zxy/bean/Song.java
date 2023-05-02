package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Song extends BaseBean {
    private Singer singer;
    private String songAlbum;
    private int songRemove;

}
