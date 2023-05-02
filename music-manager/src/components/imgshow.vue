<template>
  <div>
    <el-upload :action="uploadUrl" ref="elupload" :disabled="!enableEdit"
               list-type="picture-card"
               :file-list="uploadFileList"
               multiple
               :headers="headers"
               :data="formData"
               :auto-upload="autoUpload"
               :on-success="handleUploadSuccess"
               :on-error="handleUploadError"
               :on-change="handleChange"
               :before-upload="handleBeforeUpload"
               :on-preview="handlePictureCardPreview"
               :on-remove="handleRemove">
      <i class="el-icon-plus" v-if="enableEdit"></i>
      <div slot="tip" class="el-upload__tip"  v-if="enableEdit">{{onlyImage?'只能上传jpg/png文件，':''}}当个文件大小不超过{{maxSize}}MB</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import { delFileinfo } from "@/api/system/fileinfo";

export default {
  name:'imgShow',
  data() {
    return {
      context: process.env.VUE_APP_BASE_API,
      allFileList:[],
      uploadFileList:[],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      uploadIndex:0,
      uploadUrl: process.env.VUE_APP_BASE_API + "/system/fileinfo/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      formData:{
        fileType:undefined,
        entityType:undefined,
        entityId:undefined,
        entityNo:undefined
      }
    };
  },
  props: {
    autoUpload:{
      type:Boolean, default:true
    },
    onlyImage:{//只允许图片
      type:Boolean, default:true
    },
    maxSize:{//单个文件最大值 MB
      type:Number, default:10
    },
    fileType: {
      type: String,
      default: "",
    },
    entityType:{
      type:String,
      default: ""
    },
    entityId:{
      type:String,
      default:'',
    },
    enableEdit:{
      type:Boolean,
      default:true
    },
    entityNo:{
      type:String,
      default:'',
    },
    index:{
      type:Number, default:0
    },
    fileList:{
      type:Array,default:function () {
        return [];
      }
    }
  },
  created() {
    this.formData.fileType = this.fileType;
    this.formData.entityType = this.entityType;
    this.formData.entityId = this.entityId;
    this.formData.entityNo = this.entityNo;
    this.uploadIndex = this.index;

    for(var i=0;i<this.fileList.length;i++) {
      var fileInfo = this.fileList[i];
      this.uploadFileList.push({
        name:'file'+i,url: this.context + fileInfo.path,fileId:fileInfo.fileId
      })
    }
  },
  methods: {
    upload(params, index) {
      if (this.allFileList.length==0) {
        this.$emit("finish", {index:index, fileList:[]});
        return;
      }

      if (index!=undefined) {
        this.uploadIndex = index;
      }

      if (params!=undefined) {
        this.formData.fileType = params.fileType;
        this.formData.entityType = params.entityType;
        this.formData.entityId = params.entityId;
        this.formData.entityNo = params.entityNo;
      }

      this.$refs.elupload.submit();
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleChange(file, fileList) {
      this.allFileList = fileList;
    },
    handleBeforeUpload(file) {
      if(this.formData.entityId == undefined){
        this.msgError("未提供关联实体");
        return false;
      }

      if (this.onlyImage) {
        const isImg = (file.type === 'image/jpeg' || file.type === 'image/png');
        if (!isImg) {
          this.$message.error('上传文件只能是 JPG/PNG 格式图片!');
          return false;
        }
      }

      const isLt2M = file.size / 1024 / 1024 < this.maxSize;
      if (!isLt2M) {
        this.$message.error('单个上传文件大小不能超过 '+this.maxSize+'MB!');
        return false;
      }

      this.loading = this.$loading({
        lock: true,
        text: "上传中",
        background: "rgba(0, 0, 0, 0.7)",
      });
    },
    handleRemove(file, fileList) {
      if (file.fileId!=null && file.fileId!=undefined) {
        delFileinfo(file.fileId).then(res=>{

        })
      }
    },
    handleUploadError(res) {
      this.loading.close();
      this.$emit("error", {index:this.uploadIndex, ...res});
      for(var i=0;i<fileList.length;i++) {
        if (fileList[i].status == 'success' || fileList[i].status == 'fail') {
          continue;
        } else {
          return;
        }
      }
      this.$emit("finish", {index:this.uploadIndex, fileList:fileList});
    },
    handleUploadSuccess(res, file, fileList) {
      this.loading.close();
      this.$emit("success", {index:this.uploadIndex, ...res});

      for(var i=0;i<fileList.length;i++) {
        if (fileList[i].status == 'success' || fileList[i].status == 'fail') {
          continue;
        } else {
          return;
        }
      }
      this.$emit("finish", {index:this.uploadIndex, fileList:fileList});
    }
  },
  watch: {},
};
</script>
