<template>
  <div>
    <el-button type="primary" @click="addPlaylist">新增歌单</el-button>
    <el-table :data="playlists" style="width: 100%">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="name" label="歌单名称" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="songCount" label="歌曲数" />
      <el-table-column prop="status" label="歌单状态">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? '启用' : '冻结' }}
          <el-button type="text" size="mini" @click="toggleStatus(scope.row)">
            {{ scope.row.status === 1 ? '冻结' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="创建时间" />
      <el-table-column prop="remark" label="备注" />
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="editPlaylist(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="toggleStatus(scope.row)">
            {{ scope.row.status === 1 ? '冻结' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" title="编辑歌单">
      <el-form :model="currentPlaylist" label-width="80px">
        <el-form-item label="歌单名称">
          <el-input v-model="currentPlaylist.name" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="currentPlaylist.username" />
        </el-form-item>
        <el-form-item label="歌曲数">
          <el-input-number v-model="currentPlaylist.songCount" :min="0" />
        </el-form-item>
        <el-form-item label="歌单状态">
          <el-radio-group v-model="currentPlaylist.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">冻结</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker v-model="currentPlaylist.createdAt" type="datetime" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="currentPlaylist.remark" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePlaylist">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name:'listmanager',
    data() {
      return {
        playlists: [
          { id: 1, name: 'playlist 1', username: 'user 1', songCount: 10, status: 1, createdAt: '2021-01-01 10:00:00', remark: '' },
          { id: 2, name: 'playlist 2', username: 'user 2', songCount: 20, status: 0, createdAt: '2021-01-02 10:00:00', remark: '' },
          { id: 3, name: 'playlist 3', username: 'user 3', songCount: 30, status: 1, createdAt: '2021-01-03 10:00:00', remark: '' },
        ],
        dialogVisible: false,
        currentPlaylist: {},
      };
    },
    methods: {
      addPlaylist() {
        this.currentPlaylist = {};
        this.dialogVisible = true;
      },
      editPlaylist(playlist) {
        this.currentPlaylist = Object.assign({}, playlist);
        this.dialogVisible = true;
      },
      savePlaylist() {
        if (this.currentPlaylist.id) {
          // edit existing playlist
          const index = this.playlists.findIndex(p => p.id === this.currentPlaylist.id);
          if (index !== -1) {
            this.playlists.splice(index, 1, this.currentPlaylist);
          }
        } else {
          // add new playlist
          const lastPlaylist = this.playlists[this.playlists.length - 1];
          const newPlaylist = Object.assign({}, this.currentPlaylist, {
            id: lastPlaylist ? lastPlaylist.id + 1 : 1,
          });
          this.playlists.push(newPlaylist);
        }
        this.dialogVisible = false;
      },
      toggleStatus(playlist) {
        playlist.status = playlist.status === 1 ? 0 : 1;
      },
    },
  };
</script>