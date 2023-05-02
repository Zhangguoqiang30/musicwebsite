<template>
  <div>
    <el-container>
      <el-header style="border: 2px solid skyblue;height: 100px;">
        <el-row>
          <el-col :span="8">
            <div class="grid-content bg-purple">
              <div style="height: 100px;line-height: 100px;text-align: left">
                <img :src="logo">
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="grid-content bg-purple-light">
              <el-input style="width: 50%;" v-model="searchParam.searchVal" clearable placeholder="请输入内容"></el-input>
              <el-button icon="el-icon-search" style="margin-left: 2%;" @click="sendData()" circle></el-button>
            </div>
          </el-col>
          <el-col :span="8" style="text-align: right;padding-right: 10%;">
            <div class="grid-content bg-purple">
              <el-avatar :size="50">admin</el-avatar>
            </div>
          </el-col>
        </el-row>
      </el-header>
      <el-container>
        <!--        <el-aside >-->
        <div>
          <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
            <el-radio-button :label="false">展开</el-radio-button>
            <el-radio-button :label="true">收起</el-radio-button>
          </el-radio-group>
          <el-menu router
                   default-active="1-4-1"
                   class="el-menu-vertical-demo"
                   @open="handleOpen"
                   @close="handleClose"
                   :collapse="isCollapse">
            <el-menu-item index="/">
              <i class="el-icon-menu"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-menu-item index="/usermanager">
              <i class="el-icon-menu"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
            <el-menu-item index="/singermanager">
              <i class="el-icon-document"></i>
              <span slot="title">歌手管理</span>
            </el-menu-item>
            <el-menu-item index="/listmanager">
              <i class="el-icon-setting"></i>
              <span slot="title">歌单管理</span>
            </el-menu-item>
            <el-menu-item index="/test">
              <i class="el-icon-setting"></i>
              <span slot="title">测试</span>
            </el-menu-item>
          </el-menu>
        </div>
        <!--        </el-aside>-->
        <el-main>
          <div style="height: 15px;"></div>
          <el-divider></el-divider>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import logo from '../../assets/logo.png';
import bus from '../../utils/bus.js'


export default {
  name: "index",
  components: {},
  data() {
    return {
      logo,
      isCollapse: true,
      searchParam:{
        searchVal: '',
        flag: false,//按钮点击标识
      }
    }
  },
  methods: {
    sendData() {
      this.searchParam.flag = true; //标识点击了搜索按钮
      bus.$emit('sendData', this.searchParam) //发送数据
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.el-main, .el-container, .el-aside, .el-header {
  margin: 0;
  padding: 0;
  border: 2px solid skyblue
}

.el-header {
  line-height: 100px;
  height: 100px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

img {
  margin-top: 10px;
  margin-left: 10px;
}


</style>