package com.zxy.controller;

import com.zxy.bean.MusicFile;
import com.zxy.bean.User;
import com.zxy.service.HTTPService;
import com.zxy.service.MySystem;
import com.zxy.utils.*;
import com.zxy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MySystem mySystem;

    @Autowired
    private HTTPService httpService;

    @GetMapping("/getUserList")
    public Result<List<User>> getUserList() {
//        mySystem.InsertSinger();
        Map<String, String> map = httpService.getSongInfo("https://www.xzmp3.com/xiazai/错位时空.htm","艾辰");
        httpService.mAsyncDownload("错位时空"+Constant.SONG_SUFFIX);
        Set<String> set = map.keySet();
        for (String key : set) {
            System.out.println(key+"---"+map.get(key));
        }


        List<User> users = userService.selectMusicUser(null);
        Result result = Result.getResult().success(users);
        log.info(result.toString());
        return result;
    }

    @PostMapping("/upload2head")
    public void upload(MultipartFile file, String uploadNo, String uploadType) {
        MusicFile musicFile = new MusicFile();
        musicFile.setFileType(Integer.parseInt(uploadType)); //类型为头像
        musicFile.setPrimaryKey(new PlanKey().musicFileKey());//生成主键
        boolean a = mySystem.upload2DB(file, musicFile, uploadNo);//存储数据库
        boolean b = mySystem.upload2Minio(file, musicFile);//上传minio服务器
        if (a && b)
            log.info("插入成功！");
        else
            log.info("插入失败！");
    }


    @PutMapping("/updateUserById")
    public Result updateUser(@RequestBody User user) {
        Integer result = userService.updateUser(user);
        return result > 0 ? Result.getResult().success(null) : Result.getResult().error("修改失败");
    }


    @GetMapping("/restPWD")
    public Result restPWD(User user) {
        String msg = mySystem.resetPWD(user);
        return msg != null ? Result.getResult().success(msg) : Result.getResult().error("位置错误，联系管理员！");
    }

}
