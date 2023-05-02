package com.zxy.service.impl;

import com.zxy.bean.ListBean;
import com.zxy.bean.User;
import com.zxy.bean.vo.MinioConfig;
import com.zxy.service.UserService;
import com.zxy.mapper.UserMapper;
import com.zxy.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import java.util.List;

import static org.apache.poi.poifs.crypt.HashAlgorithm.md5;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AsyncTask asyncTask; //异步任务

    @Autowired
    private RedisUtils redisUtils;



    /**
     * 查询用户列表
     * @param user
     * @return
     */
    @Override
    public List<User> selectMusicUser(User user) {
        List<User> users = userMapper.selectMusicUser(user);
        for (User u : users) {
            String publicStr = "";
            List<ListBean> listBeans = selectUserList(u);
            for (ListBean listBean : listBeans) {
               if(listBean!=null){
                   publicStr += listBean.getListName()+",";
               }
            }
            if(!StringUtils.isEmpty(publicStr)) {
                u.setPublicStr(publicStr.substring(0, publicStr.length() - 1));
            }
        }
//        asyncTask.recordSetData(redisUtils,"getUserList",users.toString(),"测试reids");  //异步任务
        asyncTask.recordSetData2(redisUtils,"getUserList1",users.toString());  //异步任务
        return users;
    }

    /**
     * 查询用户歌单
     * @param user
     * @return
     */
    @Override
    public List<ListBean> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUser(user);
    }

}
