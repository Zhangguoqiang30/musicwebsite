<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <div> {{txt}} </div>
    <el-button @click="tests"> 点击</el-button>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import {user} from "@/api/user";

export default {
  name: 'Home',
  components: {
    HelloWorld
  },
  data(){
    return{
      txt:'',
      data:''
    }
  },
  methods:{
    tests(){
      user().then(res=>{
        console.log(res)
        this.txt = res;
      } )
    }
  }
}
</script>
