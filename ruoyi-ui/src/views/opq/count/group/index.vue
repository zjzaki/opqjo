<template>
  <div class="app-container">

    <el-table v-loading="loading" :data="groupMsgList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="群号" align="center" prop="groupCode"/>
      <el-table-column label="群名" align="center" prop="groupCode">
        <template slot-scope="scope">
          {{ groupList[groupList.findIndex((group) => group.groupCode === scope.row.groupCode)].groupName }}
        </template>
      </el-table-column>
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
          <span>{{ parseTime(scope.row.date, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="最后发言的时间" aligin="center" key="lastSpeakTime" prop="lastSpeakTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastSpeakTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {listAllGroup,refreshGroup, listGroup, getGroup, delGroup, addGroup, updateGroup} from "@/api/opq/group";
import {sendGroupText} from "@/api/opq/action";
import {getGroupMsgCount} from "@/api/opq/msgCount";


export default {
  name: "Group",
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
      // 群组表格数据
      groupList: [],
      //消息统计列表
      groupMsgList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        groupName: null,
        memberCnt: null,
        groupCnt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询群组列表 */
    getList() {
      this.loading = true;
      listAllGroup().then(response => {
        this.groupList = response.data;
        this.getGroupMsgList();
        this.loading = false;
      });
    },
    getGroupMsgList(){
      getGroupMsgCount().then(response =>{
        this.groupMsgList = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        groupCode: null,
        groupName: null,
        memberCnt: null,
        groupCnt: null,
        createTime: null
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.groupCode)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    }
  }
};
</script>
