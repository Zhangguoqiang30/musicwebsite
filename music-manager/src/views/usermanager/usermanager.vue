<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%;margin-top: 10px;">
      <el-table-column
          label="ID"
          prop="primaryKey"
          width="150">
        <!--        :index="indexAdd" 加到上一行-->
      </el-table-column>
      <el-table-column
          prop="userHead.fileUrl"
          label="头像"
          width="180">
        <template v-slot="scope">
          <img :src="'api'+scope.row.userHead.fileUrl" min-width="70" height="70"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="userName"
          label="姓名">
      </el-table-column>
      <el-table-column
          prop="userLoginName"
          label="登录名">
      </el-table-column>
      <el-table-column
          prop="singerDate"
          label="注册日期">
      </el-table-column>
      <el-table-column
          prop="publicStr"
          label="歌单">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="150">
        <template #default="scope">
          <el-button @click="handleClick(scope.row)" link type="primary" size="small">密码重置</el-button>
          <el-button type="warning" link size="small" @click="openDialog(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div>
      <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%"
          :before-close="handleClose">
        <el-form ref="form" :label-position="labelPosition" label-width="80px" :model="form">
          <el-form-item label="I     D">
            <el-input v-model="form.primaryKey" disabled></el-input>
          </el-form-item>
          <el-form-item label="头    像">
            <el-upload
                ref="upload"
                class="avatar-uploader"
                :action="url"
                :data="uploadData"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="姓    名">
            <el-input v-model="form.userName"></el-input>
          </el-form-item>
          <el-form-item label="登 录 名">
            <el-input v-model="form.userLoginName"></el-input>
          </el-form-item>
          <el-form-item label="注册日期">
            <el-date-picker clearable
                            v-model="form.singerDate"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="暂无~">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="歌单">
            <el-input v-model="form.publicStr" disabled></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateUser">确 定</el-button>
  </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {getUserList, resetPWD, updateUser} from "@/api/user";

export default {
  name: "usermanager",
  data() {
    return {
      tableData: [],
      imageUrl: '',
      indexAdd: 1,
      dialogVisible: false,
      labelPosition: 'right',
      url: "api/user/upload2head",
      uploadData: {
        uploadNo: '',
        uploadType: '1' //1代表头像
      },
      form: {
        primaryKey: '',
        userId: '',
        userName: '',
        userLoginName: '',
        singerDate: '',
        publicStr: ''
      }
    }
  },

  methods: {
    getList() {
      getUserList().then(res => {
        console.log(res)
        this.tableData = res.data;
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    openDialog(row) {
      this.dialogVisible = true; //开启模态框
      this.imageUrl = 'api' + row.userHead.fileUrl; //获取头像地址
      this.uploadData.uploadNo = row.primaryKey; //将用户id赋值传到图片接口用户更新用户表中的头像字段

      //表单赋值
      this.form.primaryKey = row.primaryKey;
      this.form.userId = row.userId;
      this.form.userName = row.userName;
      this.form.userLoginName = row.userLoginName;
      this.form.singerDate = row.singerDate;
      this.form.publicStr = row.publicStr;
      console.log(this.dialogVisible)
      console.log(row)
    },
    updateUser() {
      console.log("获取的用户编号：" + this.uploadNo);
      this.$refs["form"].validate(valid => {
        if (valid) {  //validate为true时代表表单不为空
          // this.$refs.upload.submit(); //开始上传
          updateUser(this.form).then(res => {
            console.log("name:" + this.form.userName)
            if (res.code == 200) {
              this.msgSuccess("修改成功");
              this.dialogVisible = false;
              this.$emit("finish", '1');
            } else {
              this.msgError(res.msg);
              this.$emit("finish", '1');
            }
          });
        } else {
          this.msgError('未知错误！');
        }
      });
    },
    handleClick(row) {
      this.form.primaryKey = row.primaryKey; //获取用户编号
      resetPWD(this.form).then(res => {
        console.log(res.data);
        const msg = res.code == 200 ? res.data : '系统错误，请联系管理员处理';
        this.open(msg);
      })
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log("this.imageUrl:" + this.imageUrl)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    open(msg) {
      this.$alert(msg, '重置结果', {
        confirmButtonText: '确定',
        callback: action => {
          this.$message({
            type: 'info',
            message: `action: ${action}`
          });
        }
      });
    }
  },
  created() {
    this.getList();
  }
}
</script>

<style scoped>

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>
