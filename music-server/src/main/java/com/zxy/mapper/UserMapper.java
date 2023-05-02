package com.zxy.mapper;

import com.zxy.bean.ListBean;
import com.zxy.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> selectMusicUser(User user);

    public List<ListBean> selectUserList(User user);

    public Integer updateUser(User user);

}
