- manager_user music_user  ==> 系统管理员表和用户表
- music_file ==> 文件表 type : 1(头像)
- 



Brew安装的软件在
 /usr/local/Cellar/r目录下


Minio 启动命令：


/usr/local/opt/minio/bin/minio server --config-dir=/usr/local/etc/minio --address=:9000 /usr/local/var/minio


redis启动命令：


 brew services start redis