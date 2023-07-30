<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="tagName"
            placeholder="请输入分组名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div style="height: 500px;">
          <el-scrollbar style="height:100%;">
            <ul class="tag-scroll">
              <li v-for="tag in tagList" @click="handleTagClick(tag.tagId)">{{tag.tagName}}</li>
            </ul>
          </el-scrollbar>
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="用户昵称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入用户名称"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-refresh"
              size="mini"
              @click="handleRefresh"
              v-hasPermi="['opq:tag:refresh']"
            >强制刷新
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="账号" align="center" key="uin" prop="uin" v-if="columns[0].visible" width="100"/>
          <el-table-column label="昵称" align="center" key="nick" prop="nick" v-if="columns[1].visible" :show-overflow-tooltip="true" width="200"/>
          <el-table-column label="头像" align="center" key="head" prop="head" v-if="columns[2].visible">
            <template slot-scope="scope">
              <el-image
                style="width: 50px; height: 50px"
                :src="'http://q1.qlogo.cn/g?b=qq&nk='+scope.row.uin+'&s=640'"
                :preview-src-list="['http://q1.qlogo.cn/g?b=qq&nk='+scope.row.uin+'&s=640']">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column label="性别" align="center" key="sex" v-if="columns[3].visible" width="80">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.sex === '1'" effect="dark">男</el-tag>
              <el-tag v-else-if="scope.row.sex === '2'" effect="dark" type="danger">女</el-tag>
              <el-tag v-else type="warning">未知</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="个性签名" align="center" key="signature" prop="signature" v-if="columns[4].visible" width="300" />
          <el-table-column label="分组" align="center" key="tagId" v-if="columns[5].visible" width="100">
            <template slot-scope="scope">
              <el-tag effect="dark">{{tagList[tagList.findIndex((tag)=> tag.tagId === scope.row.tagId)].tagName}}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                           v-hasPermi="['opq:action:friend:text', 'system:user:edit']">
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="sendFriendText" icon="el-icon-key"
                                    v-hasPermi="['opq:action:friend:text']">发送文本消息</el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                                    v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listTag,refresh} from "@/api/opq/tag";
import {listFriend} from "@/api/opq/friend";
import {sendFriendText} from "@/api/opq/action"

export default {
  name: "User",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      //好友分组列表
      tagList: [],
      //分组名
      tagName: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        nick: undefined,
        uin: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `账号`, visible: true },
        { key: 1, label: `昵称`, visible: true },
        { key: 2, label: `头像`, visible: true },
        { key: 3, label: `性别`, visible: true },
        { key: 4, label: `个性签名`, visible: true },
        { key: 5, label: `分组`, visible: true },
      ]
    };
  },
  watch: {
    // 根据名称筛选部门树
    // deptName(val) {
    //   this.$refs.tree.filter(val);
    // }
  },
  created() {
    this.getTagList();
    this.getList();
  },
  methods: {
    /** 得到好友分组列表 */
    getTagList(){
      listTag().then(response =>{
        this.tagList = response.rows;
      })
    },
    /** 点击分组选择 */
    handleTagClick(tagId){
      console.log(tagId);
    },
    /**强制刷新*/
    handleRefresh(){
      this.$modal.loading("正在刷新中");
      refresh().then(response=>{
        if(response.code === 200){
          this.$modal.msgSuccess("强制刷新成功");
          this.$modal.closeLoading();
          this. getList();
          this.getTagList();
        }
      })
    },

    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listFriend(this.queryParams).then(response =>{
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.tagId = undefined;
      this.queryParams.nick = "";
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "sendFriendText":
          this.sendFriendText(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    //发送好友文本消息
    sendFriendText(row){
      this.$prompt('请输入要发给"' + row.nick + '"的文本消息', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false
      }).then(({ value }) => {
        sendFriendText(row.uin, value).then(response => {
          this.$modal.msgSuccess("发送成功");
        });
      }).catch(() => {});
    }

  }
};
</script>

<style scoped>

.tag-scroll{

}
.tag-scroll li{
  list-style: none;
  width: 140px;

  padding: 4px 0 4px 0 ;
  margin-left: 0;
}
.tag-scroll li:hover{
  cursor: pointer;
  background-color: #d7d0d0;
}
</style>
