package com.zxy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zxy.bean.MusicFile;
import com.zxy.bean.Singer;
import com.zxy.bean.User;
import com.zxy.bean.vo.MinioConfig;
import com.zxy.mapper.SystemMapper;
import com.zxy.service.MySystem;
import com.zxy.utils.Md5Utils;
import com.zxy.utils.MinioUtils;
import com.zxy.utils.Result;
import com.zxy.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MySystemImpl implements MySystem {

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private SystemMapper systemMapper;


    @Override
    @Transactional
    public boolean upload2DB(MultipartFile file, MusicFile musicFile, String uploadNo) {
        Integer result1 = 0;
        Integer result2 = 0;
        if (file != null) {
            String url = "/system/download?bucket=" + minioConfig.getFileBucketName() + "&object=" + file.getOriginalFilename();
            musicFile.setFileUrl(url); //设置url
            musicFile.setFileName(file.getOriginalFilename());//设置文件名称
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                musicFile.setFileDate(sdf.parse(sdf.format(new Date())));  //设置上传/更新日期
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result1 = systemMapper.upload2DB(musicFile); //文件表做新增（无论是更新或者新增业务，文件表始终新增）
            result2 = systemMapper.uploadUserHeard(musicFile, uploadNo);//更新用户表（头像字段,uploadNo代表用户表中的user_id）
        }
        return result1 > 0 && result2 > 0;
    }

    @Override
    public boolean upload2Minio(MultipartFile file, MusicFile musicFile) {
        String result = "";
        String originalFilename = file.getOriginalFilename();
        log.info(originalFilename);
        result = minioUtils.upload(file, minioConfig.getFileBucketName());
        return result.length() > 0;
    }

    @Override
    @Transactional
    public String resetPWD(User user) {
        String newPWD = StringUtils.getPsw(6);
        String msg = "密码重置成功！" + "您的新密码为：" + newPWD;
        log.info(msg);
        user.setUserPassword(Md5Utils.hash(newPWD));
        return systemMapper.resetPWD(user) > 0 ? msg : null;

    }

    @Override
    public void InsertSinger() {
        String json = "[\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4558,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhoujielun\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"周杰伦\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000025NhlN2yWrP4.webp\",\n" +
                "    \"SINGER_REMARK\": \"Jay Chou\",\n" +
                "    \"singer_mid\": \"0025NhlN2yWrP4\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4286,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"linjunjie\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"林俊杰\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001BLpXF2DyJe2.webp\",\n" +
                "    \"SINGER_REMARK\": \"JJ Lin\",\n" +
                "    \"singer_mid\": \"001BLpXF2DyJe2\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 13948,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"g.e.m. dengziqi\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"G.E.M. 邓紫棋\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001fNHEf1SFEFN.webp\",\n" +
                "    \"SINGER_REMARK\": \"Gloria Tang\",\n" +
                "    \"singer_mid\": \"001fNHEf1SFEFN\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 5062,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"xuezhiqian\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"薛之谦\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002J4UUk29y8BY.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002J4UUk29y8BY\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 143,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"chenyixun\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"陈奕迅\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003Nz2So3XXYek.webp\",\n" +
                "    \"SINGER_REMARK\": \"Eason Chan\",\n" +
                "    \"singer_mid\": \"003Nz2So3XXYek\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 1400123,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"gaowuren\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"告五人\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002pfVN53lKI7x.webp\",\n" +
                "    \"SINGER_REMARK\": \"Accusefive\",\n" +
                "    \"singer_mid\": \"002pfVN53lKI7x\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 6016498,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangjingwenbupang\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王靖雯\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002YetSZ06c9c9.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002YetSZ06c9c9\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 60505,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"lironghao\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"李荣浩\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000aHmbL2aPXWH.webp\",\n" +
                "    \"SINGER_REMARK\": \"Ronghao Li\",\n" +
                "    \"singer_mid\": \"000aHmbL2aPXWH\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 112,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"caijianya\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"蔡健雅\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000hNnWC3kko2c.webp\",\n" +
                "    \"SINGER_REMARK\": \"Tanya Chua\",\n" +
                "    \"singer_mid\": \"000hNnWC3kko2c\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 161301,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangyuan\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王源\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000IBYF50SRnXP.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"000IBYF50SRnXP\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 7221,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"xusong\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"许嵩\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000CK5xN3yZDJt.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"000CK5xN3yZDJt\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 7588624,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"suxingjie\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"苏星婕\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002lKaDq2lLLtk.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002lKaDq2lLLtk\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 199509,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhoushen\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"周深\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003fA5G40k6hKc.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003fA5G40k6hKc\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 2150959,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"duichang\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"队长\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002rHyN14UyyaW.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002rHyN14UyyaW\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4553007,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"shidaishaoniantuan\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"时代少年团\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000039JTTG0s4SCv.webp\",\n" +
                "    \"SINGER_REMARK\": \"TNT\",\n" +
                "    \"singer_mid\": \"0039JTTG0s4SCv\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4246,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"chengxiang\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"程响\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003iPzNg35cWzp.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003iPzNg35cWzp\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 1190986,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"blackpink\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"BLACKPINK\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003DBAjk2MMfhR.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003DBAjk2MMfhR\",\n" +
                "    \"area_id\": 2,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 29858,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"renran\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"任然\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M00000067r4p0wBDDN.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"00067r4p0wBDDN\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 2,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"beyond\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"BEYOND\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002pUZT93gF4Cu.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002pUZT93gF4Cu\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 74,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wuyuetian\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"五月天\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000Sp0Bz4JXH0o.webp\",\n" +
                "    \"SINGER_REMARK\": \"Mayday\",\n" +
                "    \"singer_mid\": \"000Sp0Bz4JXH0o\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 5440133,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zyboyzhongyu\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"Zyboy忠宇\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002bX9V73wkzzM.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002bX9V73wkzzM\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 2614601,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"lanxinyu\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"蓝心羽\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003Rc7tU01TXKL.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003Rc7tU01TXKL\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 1507534,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"maobuyi\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"毛不易\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001BHDR33FZVZ0.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001BHDR33FZVZ0\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3954,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangsulong\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"汪苏泷\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001z2JmX09LLgL.webp\",\n" +
                "    \"SINGER_REMARK\": \"Silence Wang\",\n" +
                "    \"singer_mid\": \"001z2JmX09LLgL\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 6499,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhangjie\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"张杰\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002azErJ0UcDN6.webp\",\n" +
                "    \"SINGER_REMARK\": \"Jason Zhang\",\n" +
                "    \"singer_mid\": \"002azErJ0UcDN6\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 264,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangfei\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王菲\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000GDDuQ3sGQiT.webp\",\n" +
                "    \"SINGER_REMARK\": \"Faye Wong\",\n" +
                "    \"singer_mid\": \"000GDDuQ3sGQiT\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4418,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"guoding\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"郭顶\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000002ankM4d5yZI.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"0002ankM4d5yZI\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 5986851,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"旺仔小乔\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000045G50v35VUMG.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"0045G50v35VUMG\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 199515,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhangbichen\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"张碧晨\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000003ZpE43ypssl.webp\",\n" +
                "    \"SINGER_REMARK\": \"Diamond\",\n" +
                "    \"singer_mid\": \"0003ZpE43ypssl\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 265,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wanglihong\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王力宏\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001JDzPT3JdvqK.webp\",\n" +
                "    \"SINGER_REMARK\": \"Leehom Wang\",\n" +
                "    \"singer_mid\": \"001JDzPT3JdvqK\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4365,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhouchuanxiong\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"周传雄\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004NMZuf2BLjg8.webp\",\n" +
                "    \"SINGER_REMARK\": \"Steve Chou\",\n" +
                "    \"singer_mid\": \"004NMZuf2BLjg8\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 224,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhangshaohan\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"张韶涵\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002raUWw3PXdkT.webp\",\n" +
                "    \"SINGER_REMARK\": \"Angela Zhang\",\n" +
                "    \"singer_mid\": \"002raUWw3PXdkT\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4615,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"liyuchun\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"李宇春\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002ZOuVm3Qn20Y.webp\",\n" +
                "    \"SINGER_REMARK\": \"Chris Lee\",\n" +
                "    \"singer_mid\": \"002ZOuVm3Qn20Y\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3344766,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"en\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"en\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001IkitI4Gcu5u.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001IkitI4Gcu5u\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 109,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"sunyanzi\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"孙燕姿\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001pWERg3vFgg8.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001pWERg3vFgg8\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4605082,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"shiqishuni\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"七叔（叶泽浩）\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004gGNH91beMrM.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"004gGNH91beMrM\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 2907567,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"dengshimejun\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"邓寓君(等什么君)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001B2drs3Jq4EX.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001B2drs3Jq4EX\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3158196,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"daiyutong\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"戴羽彤\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001yfOTl2jS0xm.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001yfOTl2jS0xm\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 11921,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"Taylor Swift\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"Taylor Swift (泰勒·斯威夫特)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000qrPik2w6lDr.webp\",\n" +
                "    \"SINGER_REMARK\": \"泰勒·斯威夫特\",\n" +
                "    \"singer_mid\": \"000qrPik2w6lDr\",\n" +
                "    \"area_id\": 3,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 38578,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"exo\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"EXO (엑소)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001QVwtq3l8cKC.webp\",\n" +
                "    \"SINGER_REMARK\": \"엑소\",\n" +
                "    \"singer_mid\": \"001QVwtq3l8cKC\",\n" +
                "    \"area_id\": 2,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4731886,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"yoasobi\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"YOASOBI (ヨアソビ)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001Erp1x1jDOoQ.webp\",\n" +
                "    \"SINGER_REMARK\": \"ヨアソビ\",\n" +
                "    \"singer_mid\": \"001Erp1x1jDOoQ\",\n" +
                "    \"area_id\": 2,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 174,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhangxueyou\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"张学友\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004Be55m1SJaLk.webp\",\n" +
                "    \"SINGER_REMARK\": \"Jacky Cheung\",\n" +
                "    \"singer_mid\": \"004Be55m1SJaLk\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 11733,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"bigbang\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"BIGBANG (빅뱅)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004AlfUb0cVkN1.webp\",\n" +
                "    \"SINGER_REMARK\": \"빅뱅\",\n" +
                "    \"singer_mid\": \"004AlfUb0cVkN1\",\n" +
                "    \"area_id\": 2,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 54,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"mowenwei\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"莫文蔚\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000cISVf2QqLc6.webp\",\n" +
                "    \"SINGER_REMARK\": \"Karen Mok\",\n" +
                "    \"singer_mid\": \"000cISVf2QqLc6\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4055289,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"aliyue\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"阿梨粤\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002ffRcI0XN8px.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002ffRcI0XN8px\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 43665,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"mengran\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"梦然\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004dr7ZS36C2B1.webp\",\n" +
                "    \"SINGER_REMARK\": \"Miya\",\n" +
                "    \"singer_mid\": \"004dr7ZS36C2B1\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 16244,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"xujiaying\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"徐佳莹\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002LZVMH0zc8F4.webp\",\n" +
                "    \"SINGER_REMARK\": \"LALA Xu\",\n" +
                "    \"singer_mid\": \"002LZVMH0zc8F4\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 6597642,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"in-k\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"IN-K\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002yupVT2XEFaA.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002yupVT2XEFaA\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 27158,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"Imagine Dragons\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"Imagine Dragons (梦龙)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003W0GsQ2bAxVU.webp\",\n" +
                "    \"SINGER_REMARK\": \"梦龙\",\n" +
                "    \"singer_mid\": \"003W0GsQ2bAxVU\",\n" +
                "    \"area_id\": 3,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 165,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"xiaoyaxuan\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"萧亚轩\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002tkdEU4gLVqO.webp\",\n" +
                "    \"SINGER_REMARK\": \"Elva Hsiao\",\n" +
                "    \"singer_mid\": \"002tkdEU4gLVqO\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3127723,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"xiaoaqi\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"小阿七\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000DT3kC3IGHjR.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"000DT3kC3IGHjR\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 204664,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"luhan\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"鹿晗\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001SqkF53OEhdO.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001SqkF53OEhdO\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 6547408,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"bushihuahuoya---[replace by 6496500]\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"不是花火呀\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003LABmP0dTIWp.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003LABmP0dTIWp\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 40449,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhaolei\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"赵雷\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001Lr98T0yEWAk.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001Lr98T0yEWAk\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 101,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"taozhe\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"陶喆\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002cK0F12szD9T.webp\",\n" +
                "    \"SINGER_REMARK\": \"David Zee Tao\",\n" +
                "    \"singer_mid\": \"002cK0F12szD9T\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3487463,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"刘大壮\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002moRNc1l02Dr.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002moRNc1l02Dr\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 202043,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangxinchen\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王忻辰\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004W8PqQ0EYrEr.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"004W8PqQ0EYrEr\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 944940,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"Alan Walker\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"Alan Walker (艾兰·沃克)\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000020PeOh4ZaCw1.webp\",\n" +
                "    \"SINGER_REMARK\": \"艾兰·沃克\",\n" +
                "    \"singer_mid\": \"0020PeOh4ZaCw1\",\n" +
                "    \"area_id\": 3,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 235,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"likeqin\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"李克勤\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003nS2v740Lxcw.webp\",\n" +
                "    \"SINGER_REMARK\": \"Hacken Lee\",\n" +
                "    \"singer_mid\": \"003nS2v740Lxcw\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 1517630,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"ayueyue\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"阿YueYue\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000009dWas0XxekN.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"0009dWas0XxekN\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3417721,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"chengjiajia\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"程佳佳\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003gFfHo4H6AHY.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003gFfHo4H6AHY\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 6028,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"a-lin\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"A-Lin\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003ArN8Z0WpjTz.webp\",\n" +
                "    \"SINGER_REMARK\": \"黄丽玲\",\n" +
                "    \"singer_mid\": \"003ArN8Z0WpjTz\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 228,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"chenhuixian\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"陈慧娴\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000JvETZ3tOrPR.webp\",\n" +
                "    \"SINGER_REMARK\": \"Priscilla Chan\",\n" +
                "    \"singer_mid\": \"000JvETZ3tOrPR\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3347,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"gujuji\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"古巨基\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000NFT2p1GbnPB.webp\",\n" +
                "    \"SINGER_REMARK\": \"Leo Ku\",\n" +
                "    \"singer_mid\": \"000NFT2p1GbnPB\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4607,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhangliangying\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"张靓颖\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000aw4WC2EQYTv.webp\",\n" +
                "    \"SINGER_REMARK\": \"Jane Zhang\",\n" +
                "    \"singer_mid\": \"000aw4WC2EQYTv\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 5226906,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"jiushinafangkai\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"就是南方凯\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002zPxu14crYMp.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002zPxu14crYMp\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 4367,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangxinling\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王心凌\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003RVAdJ1YT5AI.webp\",\n" +
                "    \"SINGER_REMARK\": \"Cyndi Wang\",\n" +
                "    \"singer_mid\": \"003RVAdJ1YT5AI\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 163,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"liudehua\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"刘德华\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003aQYLo2x8izP.webp\",\n" +
                "    \"SINGER_REMARK\": \"Andy Lau\",\n" +
                "    \"singer_mid\": \"003aQYLo2x8izP\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 1296832,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"huer\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"虎二\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000030BYDJ2YJih8.webp\",\n" +
                "    \"SINGER_REMARK\": \"Tiger Wang\",\n" +
                "    \"singer_mid\": \"0030BYDJ2YJih8\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 11455,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"zhangyuan\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"张远\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004PjYVG2cjyBK.webp\",\n" +
                "    \"SINGER_REMARK\": \"Bird Zhang\",\n" +
                "    \"singer_mid\": \"004PjYVG2cjyBK\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 940748,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"chenli\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"陈粒\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000004WgCsE3KBddt.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"004WgCsE3KBddt\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3133695,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangxiaoshuai\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王小帅\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000013xwfx03qjvN.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"0013xwfx03qjvN\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 13578,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"by2\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"BY2\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000000Z1Ow71FFutX.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"000Z1Ow71FFutX\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 2911372,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"ycccc\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"ycccc\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001Jnlli222xTo.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"001Jnlli222xTo\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 11606,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"linyoujia\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"林宥嘉\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000001f0VyZ1hmWZ1.webp\",\n" +
                "    \"SINGER_REMARK\": \"Yoga Lin\",\n" +
                "    \"singer_mid\": \"001f0VyZ1hmWZ1\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 139,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"wangjie\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"王杰\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003NKwHr46UKeR.webp\",\n" +
                "    \"SINGER_REMARK\": \"Dave Wang\",\n" +
                "    \"singer_mid\": \"003NKwHr46UKeR\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 11608,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"yangzongwei\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"杨宗纬\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003tMm0y0TuewY.webp\",\n" +
                "    \"SINGER_REMARK\": \"Aska Yang\",\n" +
                "    \"singer_mid\": \"003tMm0y0TuewY\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 1399808,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"jiedaobangdc\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"街道办GDC\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000003RGAyU46oc94.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"003RGAyU46oc94\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3062965,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"yanrenzhong\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"颜人中\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M0000037vppH2drqM2.webp\",\n" +
                "    \"SINGER_REMARK\": \"ele yan\",\n" +
                "    \"singer_mid\": \"0037vppH2drqM2\",\n" +
                "    \"area_id\": 0,\n" +
                "    \"country_id\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"SINGER_ID\": 3546632,\n" +
                "    \"country\": \"\",\n" +
                "    \"spell\": \"\",\n" +
                "    \"singer_pmid\": \"\",\n" +
                "    \"trend\": 0,\n" +
                "    \"SINGER_NAME\": \"Kirsty刘瑾睿\",\n" +
                "    \"concernNum\": 0,\n" +
                "    \"SINGER_HEAD\": \"http://y.gtimg.cn/music/photo_new/T001R150x150M000002cJar62Es6zH.webp\",\n" +
                "    \"SINGER_REMARK\": \"\",\n" +
                "    \"singer_mid\": \"002cJar62Es6zH\",\n" +
                "    \"area_id\": 1,\n" +
                "    \"country_id\": 0\n" +
                "  }\n" +
                "]";


        JSONArray jsonArray = JSONObject.parseArray(json);
        List<Singer> list = new ArrayList<>();
        Singer singer = null;
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.parseObject(o.toString());
            singer = new Singer();
            singer.setSingerName(jsonObject.getString("SINGER_NAME"));
            singer.setHead(jsonObject.getString("SINGER_HEAD"));
            singer.setSingerRemake("SI" + jsonObject.getString("SINGER_ID"));
            singer.setSingerAligns(jsonObject.getString("spell"));
            singer.setSingerNation(jsonObject.getString("area_id"));
            list.add(singer);
        }

        Integer integer = systemMapper.InsertSinger(list);
        log.info("插入===========>>>>>>>:"+integer+"条数据");

    }

}


