package com.zxy.service;

import com.zxy.bean.ListBean;
import com.zxy.bean.User;
import com.zxy.utils.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public List<User> selectMusicUser(User user);

    public List<ListBean> selectUserList(User user);

    public Integer updateUser(User user);

}
