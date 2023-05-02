<template>
  <div style="width: 100%;height: 100%;">
    <div style="width: 80%;float:left;line-height: 60px;">
      <el-row>
        <el-col :span="12">
          <div class="grid-content bg-purple">
          <div style="margin-left: -250px;">
          <el-input v-model="val" :placeholder="pleace" style="width: 30%;"></el-input>
            <el-button type="primary" :icon="Search" style="margin-left: 20px;">搜索</el-button>
          </div>
        </div>
        </el-col>
        <el-col :span="12"><div class="grid-content bg-purple-light"></div></el-col>
      </el-row>

    </div>
    <div style="float: right;margin-right: 50px;line-height: 60px">{{dateVal}} <el-avatar :size="50"> user </el-avatar></div>
  </div>

</template>

<script>

export default {
  name:"header",
  data(){
    return{
      val: '',
      pleace:'请输入内容',
      dateVal:'欢迎你',
      Search:Search,
    }
  },
  methods:{
    getNow(){
      const date = new Date();
      const hour = date.getHours();
      if(hour >= 0 && hour < 8){
          this.dateVal = "凌晨好!"
      }else if(hour>=8&&hour<12){
        this.dateVal = "早上好!"
      }else if(hour==12){
        this.dateVal = "中午好!"
      }else if(hour>12&&hour<18){
        this.dateVal = "下午好!"
      }else {
        this.dateVal = "晚上好!"
      }
    }
  },
  created() {
    this.getNow();
  }
}

</script>