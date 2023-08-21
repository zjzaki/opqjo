<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="总人数" prop="memberCnt">
        <el-input
          v-model="queryParams.memberCnt"
          placeholder="请输入总人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群最高人数" prop="groupCnt">
        <el-input
          v-model="queryParams.groupCnt"
          placeholder="请输入群最高人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">-->
      <!--  &lt;!&ndash;<el-button&ndash;&gt;-->
      <!--  &lt;!&ndash;  type="primary"&ndash;&gt;-->
      <!--  &lt;!&ndash;  plain&ndash;&gt;-->
      <!--  &lt;!&ndash;  icon="el-icon-plus"&ndash;&gt;-->
      <!--  &lt;!&ndash;  size="mini"&ndash;&gt;-->
      <!--  &lt;!&ndash;  @click="handleAdd"&ndash;&gt;-->
      <!--  &lt;!&ndash;  v-hasPermi="['opq:group:add']"&ndash;&gt;-->
      <!--  >新增-->
      <!--  &lt;!&ndash;</el-button>&ndash;&gt;-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
      <!--  <el-button-->
      <!--    type="success"-->
      <!--    plain-->
      <!--    icon="el-icon-edit"-->
      <!--    size="mini"-->
      <!--    :disabled="single"-->
      <!--    @click="handleUpdate"-->
      <!--    v-hasPermi="['opq:plugin:edit']"-->
      <!--  >修改-->
      <!--  </el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
      <!--  <el-button-->
      <!--    type="danger"-->
      <!--    plain-->
      <!--    icon="el-icon-delete"-->
      <!--    size="mini"-->
      <!--    :disabled="multiple"-->
      <!--    @click="handleDelete"-->
      <!--    v-hasPermi="['opq:plugin:remove']"-->
      <!--  >删除-->
      <!--  </el-button>-->
      <!--</el-col>-->
      <!--<el-col :span="1.5">-->
      <!--  <el-button-->
      <!--    type="warning"-->
      <!--    plain-->
      <!--    icon="el-icon-download"-->
      <!--    size="mini"-->
      <!--    @click="handleExport"-->
      <!--    v-hasPermi="['opq:plugin:export']"-->
      <!--  >导出-->
      <!--  </el-button>-->
      <!--</el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefresh"
          v-hasPermi="['opq:plugin:refresh']"
        >重载插件
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pluginList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="插件id" align="center" prop="id"/>
      <el-table-column label="插件名" align="center" prop="name"/>
      <el-table-column label="插件描述" align="center" prop="desc"/>
      <el-table-column label="状态" align="center" key="enable">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enable"
            active-value="1"
            inactive-value="0"
            @change="handleEnableChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-data"
            @click="handleGroupPlugin(scope.row)"
            v-hasPermi="['opq:plugin:groupList']"
          >开启的群聊
          </el-button>
          <!--<el-button-->
          <!--  size="mini"-->
          <!--  type="text"-->
          <!--  icon="el-icon-edit"-->
          <!--  @click="handleUpdate(scope.row)"-->
          <!--  v-hasPermi="['opq:plugin:edit']"-->
          <!--&gt;修改-->
          <!--</el-button>-->
          <!--<el-button-->
          <!--  size="mini"-->
          <!--  type="text"-->
          <!--  icon="el-icon-delete"-->
          <!--  @click="handleDelete(scope.row)"-->
          <!--  v-hasPermi="['opq:plugin:remove']"-->
          <!--&gt;删除-->
          <!--</el-button>-->
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

    <!-- 添加或修改群组对话框 -->
    <el-dialog :title="title" :visible.sync="groupPluginOpen" width="500px" append-to-body>
      <el-table
        :data="tableGroupPluginData"
        style="width: 100%">
        <el-table-column label="群号" width="180" prop="groupCode"/>
        <el-table-column label="群名" width="180" prop="groupName"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="handleGroupPluginDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {refreshGroup, listGroup, getGroup, delGroup, addGroup, updateGroup} from "@/api/opq/group";
import {sendGroupText} from "@/api/opq/action";
import {
  deleteGroupPlugin,
  listGroupPlugin,
  listGroupPluginByPluginId,
  listPlugin,
  refreshPlugin
} from "../../../api/opq/plugin";


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
      // 插件表格数据
      pluginList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      groupPluginOpen: false,
      tableGroupPluginData: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询插件列表*/
    getList() {
      this.loading = true;
      listPlugin().then(response => {
        this.pluginList = response.rows;
        this.total = response.total;
        this.loading = false;
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.groupCode)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加群组";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const groupCode = row.groupCode || this.ids
      getGroup(groupCode).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群组";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.groupCode != null) {
            updateGroup(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addGroup(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const groupCodes = row.groupCode || this.ids;
      this.$modal.confirm('是否确认删除群组编号为"' + groupCodes + '"的数据项？').then(function () {
        return delGroup(groupCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('opq/group/export', {
        ...this.queryParams
      }, `groupList_${new Date().getTime()}.xlsx`)
    },
    /**强制刷新*/
    handleRefresh() {
      this.$modal.loading("正在重载插件");
      refreshPlugin().then(response => {
        if (response.code === 200) {
          this.$modal.msgSuccess("重载插件成功");
          this.$modal.closeLoading();
          this.getList();
        } else {
          this.$modal.msgError("重载插件成功");
        }
      });
    },
    /** 用户状态修改*/
    handleEnableChange(row) {
      let text = row.enable === "1" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.name + '"插件吗？').then(function () {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.enable = row.enable === "0" ? "1" : "0";
      });
    },
    /**打开开启当前插件的页面*/
    handleGroupPlugin(row) {
      //清理数据
      this.tableGroupPluginData = [];
      //打开页面
      this.groupPluginOpen = true;
      //发送请求
      listGroupPluginByPluginId(row.id).then(response => {
        console.log(response)
        if (response.code == 200) {
          this.tableGroupPluginData = response.rows;
        } else {
          this.$modal.msgError("状态码：" + response.code + "--" + response.msg);
        }
      })
    },
    /**删除开启当前插件的群聊*/
    handleGroupPluginDelete(index, row) {
      deleteGroupPlugin(row.groupCode, row.pluginId).then(response => {
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg);
          //移除数据
          this.tableGroupPluginData.splice(index, 1)
        } else {
          this.$modal.msgError("状态码：" + response.code + "--" + response.msg);
        }
      })
    }
  }
};
</script>
