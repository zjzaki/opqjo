<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--群成员数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px" @submit.native.prevent>
          <el-form-item label="用户昵称" prop="userName">
            <el-input
              v-model="queryParams.nick"
              placeholder="请输入用户昵称称"
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
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="groupMsgCountList" @selection-change="handleSelectionChange"
                  @sort-change="sortChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="群号" align="center" prop="groupCode"/>
          <el-table-column label="群名" align="center" prop="groupName"/>
          <el-table-column label="群头像" align="center" prop="">
            <template slot-scope="scope">
              <el-image
                style="width: 50px; height: 50px"
                :src="'https://p.qlogo.cn/gh/'+scope.row.groupCode+'/'+scope.row.groupCode+'/640'"
                :preview-src-list="['https://p.qlogo.cn/gh/'+scope.row.groupCode+'/'+scope.row.groupCode+'/640']">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column label="消息条数" align="center" prop="msgCount"/>
          <el-table-column label="时间" aligin="center" key="date" prop="date">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.date, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="最后发言的时间" aligin="center" key="lastSpeakTime" prop="lastSpeakTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.lastSpeakTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
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

import {getGroupMsgCountList} from "@/api/opq/msgCount"

export default {
  name: "historyData",
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
      //历史消息统计列表
      groupMsgCountList: [],
      //群组列表
      groupList: [],
      //分组名
      groupName: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupUid: undefined,
        nick: undefined,
        orderByColumn: undefined,
        isAsc: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** 查询历史消息统计列表 */
    getList() {
      this.loading = true;
      getGroupMsgCountList(this.queryParams).then(response => {
        this.groupMsgCountList = response.rows;
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
      this.queryParams.groupUid = undefined;
      this.queryParams.nick = "";
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //最后发言时间排序
    sortChange(column) {

      this.queryParams.isAsc = column.order;//动态取值排序顺序
      if (column.order == null) {
        this.queryParams.orderByColumn = undefined;//查询字段是表格中字段名字
      } else {
        this.queryParams.orderByColumn = column.prop;//查询字段是表格中字段名字
      }
      this.handleQuery();
    }
  }
};
</script>

<style scoped>

.tag-scroll {

}

.tag-scroll li {
  list-style: none;
  width: 140px;

  padding: 4px 0 4px 0;
  margin-left: 0;
}

.tag-scroll li:hover {
  cursor: pointer;
  background-color: #d7d0d0;
}
</style>
