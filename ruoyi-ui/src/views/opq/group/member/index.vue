<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--群聊数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="groupName"
            placeholder="请输入群聊名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div style="height: 600px;">
          <el-scrollbar style="height:100%;">
            <span>群聊列表</span>
            <ul class="tag-scroll">
              <li v-for="group in groupList" @click="handleTagClick(group.groupCode)">{{ group.groupName }}</li>
            </ul>
          </el-scrollbar>
        </div>
      </el-col>
      <!--群成员数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px" @submit.native.prevent>
          <el-form-item label="QQ号" prop="userName">
            <el-input
              v-model="queryParams.memberUin"
              placeholder="请输入QQ号"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
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
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange"
                  @sort-change="sortChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="群聊名" align="center" key="groupUin" prop="groupUin" width="100">
            <template slot-scope="scope">
              {{ tempGroupList[tempGroupList.findIndex((group) => group.groupCode === scope.row.groupUid)].groupName }}
            </template>
          </el-table-column>
          <el-table-column label="QQ" align="center" key="memberUin" prop="memberUin" width="100"/>
          <el-table-column label="群昵称" align="center" key="nick" prop="nick" width="120"/>
          <el-table-column label="头像" align="center" key="head" prop="head">
            <template slot-scope="scope">
              <el-image
                style="width: 50px; height: 50px"
                :src="'http://q1.qlogo.cn/g?b=qq&nk='+scope.row.memberUin+'&s=640'"
                :preview-src-list="['http://q1.qlogo.cn/g?b=qq&nk='+scope.row.memberUin+'&s=640']">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column label="权限" align="center" key="memberFlag" width="80">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.memberFlag === 1" effect="dark">群主</el-tag>
              <el-tag v-else-if="scope.row.memberFlag === 2" effect="dark" type="danger">管理员</el-tag>
              <el-tag v-else type="success" effect="dark">成员</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="活跃度" align="center" key="level" prop="level" width="80"/>
          <el-table-column
            label="最后发言时间"
            align="center"
            key="lastSpeakTime"
            prop="lastSpeakTime"
            sortable="custom"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.lastSpeakTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="加群时间" align="center" key="joinTime" prop="joinTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.joinTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" key="" width="80">
            <template slot-scope="scope">
              <el-dropdown size="mini" @command="(command) => handleActionCommand(command, scope.row)"
                           v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleBanGroupMember" icon="el-icon-key"
                                    v-hasPermi="['system:user:resetPwd']">禁言用户
                  </el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                                    v-hasPermi="['system:user:edit']">分配角色
                  </el-dropdown-item>
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
import {listAllGroup, refreshGroup} from "@/api/opq/group"
import {listMember} from "@/api/opq/groupMember"
import {banGroupMember} from "../../../../api/opq/action";

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
      //群组列表
      groupList: [],
      //临时群组列表
      tempGroupList: [],
      // 群聊名称
      groupName: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        memberUin: undefined,
        nick: undefined,
        orderByColumn: undefined,
        isAsc: undefined
      }
    };
  },
  watch: {
    // 根据名称筛选群聊
    groupName(val) {

      if (val !== '' || val !== null) {
        this.$nextTick(() => {
          this.groupList = this.tempGroupList.filter((item) => {
            return item.groupName.indexOf(val) !== -1
          });
        })

      }
    }
  },
  created() {
    this.getGroupList();
    this.getList();
  },
  methods: {

    /** 得到群组列表 */
    getGroupList() {
      listAllGroup().then(response => {
        this.groupList = response.data;
        this.tempGroupList = response.data;
      })
    },

    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listMember(this.queryParams).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },

    /** 点击分组选择 */
    handleTagClick(groupUid) {
      this.queryParams.groupUid = groupUid;
      this.handleQuery();
    },
    /**强制刷新*/
    handleRefresh() {
      this.$modal.loading("正在刷新中");
      refreshGroup().then(response => {
        if (response.code === 200) {
          this.$modal.msgSuccess("强制刷新成功");
          this.$modal.closeLoading();
          this.getList();
          this.getTagList();
        }
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
    },
    /** 禁言群组成员 */
    handleBanGroupMember(row) {
      banGroupMember(row.groupUid, row.memberUid, 60).then(response => {
        console.log(response);
        this.$modal.msgSuccess("操作成功");
      })
    },
    // 更多操作触发
    handleActionCommand(command, row) {
      switch (command) {
        case "handleBanGroupMember":
          this.handleBanGroupMember(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
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
