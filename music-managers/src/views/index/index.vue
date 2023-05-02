<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px" >
<!--           <div style="height: 600px;border-right: 2px skyblue solid">-->
<!--             <h5>默认颜色</h5>-->
        <div style="height: 60px;line-height: 60px;text-align: center;border-bottom: 1px rgba(193,217,169,0.91) solid;">
          <img :src="logo">
        </div>
             <el-menu router
                      default-active="2"
                      class="el-menu-vertical-demo"
                      @open="handleOpen"
                      @close="handleClose">
               <el-menu-item index="/"> <!-- 进入系统默认进入首页 -->
                 <i class="el-icon-menu"></i>
                 <span slot="title">系统首页</span>
               </el-menu-item><br/>
               <el-menu-item index="/usermanager">
                 <i class="el-icon-document"></i>
                 <span slot="title">用户管理</span>
               </el-menu-item><br/>
               <el-menu-item index="/singermanager">
                 <i class="el-icon-setting"></i>
                 <span slot="title">歌手管理</span>
               </el-menu-item><br/>
               <el-menu-item index="/listmanager">
                 <i class="el-icon-setting"></i>
                 <span slot="title">歌单管理</span>
               </el-menu-item>
             </el-menu>
<!--           </div>-->
      </el-aside>
      <el-container>
        <el-header>
        <div style="width: 100%; border-bottom: 1px rgba(193,217,169,0.91) solid;height: 100%">
            <heade></heade>
        </div>
        </el-header>
        <el-main>
          <div style="width: 100%;height: 40px;border-bottom: 1px rgba(193,217,169,0.91) solid">
            <el-breadcrumb separator-class="el-icon-arrow-right" :path="mpath" v-for="(item,index) in arr" >
              <el-breadcrumb-item :to="{ path: mpath }" >{{mTxt}}</el-breadcrumb-item>
<!--              <el-breadcrumb-item :to="{ path: '/' }">活动管理</el-breadcrumb-item>-->
<!--              <el-breadcrumb-item>活动列表</el-breadcrumb-item>-->
<!--              <el-breadcrumb-item>活动详情</el-breadcrumb-item>-->
            </el-breadcrumb>

          </div>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>



<script>

import logo from '../../assets/logo.png';
import heade from '@/views/header/header'


export default {
  name: "index",
  components:{heade},
data(){
    return{
      logo,
      mpath:'/',
      mTxt:'',
      isCollapse: true,
      arr:[{mpath:"/",mTxt:'首页'},{mpath:"/usermanager",mTxt:'用户管理'}]
    }
},
    methods: {
      handleOpen(key, keyPath) {
        this.mpath = keyPath;
        console.log("key:"+key, "keyPath:"+keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-aside{
  /*background-color: #a7a183;*/
  margin: 0;
  padding: 0;
}
.el-header{
  /*background-color: #a29ab6;*/
  /*border-right: 5px black solid;*/
  margin: 0;
  padding: 0;
}
.el-main{
  /*background-color: rgba(113, 61, 61, 0.91);*/
  margin: 0;
  padding: 0;
}

.el-menu{
  height: 500px;
}
.el-menu-item{
  /*border-bottom: 1px skyblue solid;*/
}


</style>