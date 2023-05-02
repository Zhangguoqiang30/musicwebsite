package com.zxy.mapper;

import com.zxy.bean.MusicFile;
import com.zxy.bean.Singer;
import com.zxy.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SystemMapper {

    public Integer upload2DB(MusicFile musicFile);

    public Integer uploadUserHeard(@Param("musicFile") MusicFile musicFile, @Param("uploadNo") String uploadNo);

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    public Integer resetPWD(User user);

    public Integer InsertSinger(@Param("list") List<Singer> list);

    //查询用户头像是否存在
//    public Integer selectUserHeard(MusicFile musicFile,String uploadNo);
}
