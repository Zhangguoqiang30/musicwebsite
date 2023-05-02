<template>
<div style="padding: 20px">
  <el-tooltip class="item" effect="dark" content="新增用户" placement="bottom">
    <el-button  style="margin-top: -5px;margin-left: -92.5%;">
      <el-icon style="vertical-align: middle">
        <CirclePlus />
      </el-icon>
      <span style="vertical-align: middle"> 新增 </span>
      用户
    </el-button>
  </el-tooltip>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%;margin-top: 10px;">
    <el-table-column
        label="序号"
        align="center"
        type="index"
        :index="indexAdd"
        width="50">
    </el-table-column>
    <el-table-column
        prop="userHead.fileUrl"
        label="头像"
        width="180">
      <template v-slot="scope">
        <img :src="'api'+scope.row.userHead.fileUrl"  min-width="70" height="70" />
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
        <el-button @click="handleClick(scope.row)" link type="primary" size="small">查看</el-button>
        <el-button type="warning" link size="small" @click="openDialog(scope.row)">编辑</el-button>
      </template>
    </el-table-column>
  </el-table>
  </div>

</div>
  <el-dialog  title="收货地址"  :visible.sync="dialogVisible">
    <el-form :model="form">
      <el-form-item label="活动名称" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="活动区域" >
        <el-select v-model="form.region" placeholder="请选择活动区域">
          <el-option label="区域一" value="shanghai"></el-option>
          <el-option label="区域二" value="beijing"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </div>
  </el-dialog>


<!-- <div style="margin-bottom: 1%;">-->
<!--   <el-pagination-->
<!--       background-->
<!--       layout="prev, pager, next"-->
<!--       :total="1000">-->
<!--   </el-pagination>-->
<!-- </div>-->

</template>

<script>
import {getUserList} from "@/api/user";

export default {
  name: "usermanager",
  data() {
    return {
      tableData: [],
      // fits: ['fill', 'contain', 'cover', 'none', 'scale-down']
      fit:'contain',
      dialogVisible: false,
      gridData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }],
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
    }

  },
  methods: {
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleClick(row) {
      console.log(row);
    },
    indexAdd(index) {
      // return index + 1 + (this.pageIndex当期页 - 1) * this.pageSize每页显示条数
      return index + 1 + (1 - 1) * 10
    },
    getImg(){
    },
    getList(){
      getUserList().then(res=>{
        console.log(res)
        this.tableData = res.data;
      })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
  openDialog(row){
    this.dialogVisible = true;
    console.log(this.dialogVisible)
  }
  },
  created() {
    this.getList();
  }
}
</script>

<style scoped>

</style>
