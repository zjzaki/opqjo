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
      <el-form-item label="群最高人数" prop="groupCnt" label-width="100px">
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['opq:group:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['opq:group:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['opq:group:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['opq:group:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefresh"
          v-hasPermi="['opq:group:refresh']"
        >强制刷新
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="groupList" @selection-change="handleSelectionChange">
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
      <el-table-column label="总人数" align="center" prop="memberCnt"/>
      <el-table-column label="群最高人数" align="center" prop="groupCnt"/>
      <el-table-column label="创建时间" aligin="center" key="createTime" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['opq:group:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['opq:group:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-chat-dot-square"
            @click="handleSendMsg(scope.row)"
            v-hasPermi="['opq:action:group:text']"
          >发消息
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-chat-dot-square"
            @click="handleGroupPlugin(scope.row)"
          >插件管理
          </el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="群名" prop="groupName">
          <el-input v-model="form.groupName" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="总人数" prop="memberCnt">
          <el-input v-model="form.memberCnt" placeholder="请输入总人数"/>
        </el-form-item>
        <el-form-item label="群最高人数" prop="groupCnt">
          <el-input v-model="form.groupCnt" placeholder="请输入群最高人数"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 群聊插件对话框 -->
    <el-dialog title="插件管理" :visible.sync="groupPluginOpen" width="1000px" append-to-body>
      <el-form ref="groupPluginForm" :model="groupPluginForm" label-width="200px">
        <el-form-item label="群号：">
          <span>{{ groupPluginForm.groupCode }}</span>
        </el-form-item>
        <el-form-item label="群名：">
          <span>{{ groupPluginForm.groupName }}</span>
        </el-form-item>
        <el-form-item label="群聊插件" prop="groupCnt">
          <el-transfer
            filterable
            :filter-method="filterMethod"
            filter-placeholder="请输入插件名"
            v-model="groupPluginValue"
            :titles="groupPluginTitle"
            :data="groupPluginData">
          </el-transfer>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitGroupPluginForm">确 定</el-button>
        <el-button @click="cancelGroupPlugin">取 消</el-button>
      </div>
    </el-dialog>

    <!--发送消息对话框-->
    <el-dialog :title="msgDialogTitle" :visible.sync="msgDialogOpen" width="800px" append-to-body>
      <el-container>
        <el-main>
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4}"
            placeholder="请输入要发送的内容"
            v-model="groupTextMsg">
          </el-input>

          <!--<img :src="imgUrl" alt="" ref="demoImg" width="200px" height="200px">-->

          <!--<input type="file" ref="file" name="file" @change="handleGroupImage">-->
        </el-main>
        <el-footer>
          <el-button type="primary" @click="handleSend">发送</el-button>
        </el-footer>
      </el-container>
    </el-dialog>
  </div>
</template>

<script>
import {listGroupMsg} from "../../../api/opq/msg";

const getTime = () => new Date().getTime();
const generateRandId = () => Math.random().toString(36).substr(-8);

import {refreshGroup, listGroup, getGroup, delGroup, addGroup, updateGroup} from "@/api/opq/group";
import {sendGroupText} from "@/api/opq/action";
import {addGroupPlugin, listGroupPlugin, listPlugin} from "../../../api/opq/plugin";
import {blobToBase64, urlToBase64} from "../../../api/tool/imgUtil";
import {sendGroupPic} from "../../../api/opq/action";


export default {
  name: "Group",
  data() {

    // const generateData = _ => {
    //   const data = [];
    //   const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
    //   const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
    //   cities.forEach((city, index) => {
    //     data.push({
    //       label: city,
    //       key: index,
    //       pinyin: pinyin[index]
    //     });
    //   });
    //   return data;
    // };

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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 群聊插件弹出层
      groupPluginOpen: false,
      //群聊插件列表标题
      groupPluginTitle: ['未开启', '已开启'],
      //群聊插件表单
      groupPluginForm: {},
      //群聊插件数据
      groupPluginData: [],
      //群聊插件数据
      groupPluginValue: [],
      //群聊图片地址
      fileImageList: '',

      //过滤方法
      filterMethod(query, item) {
        // return item.pinyin.indexOf(query) > -1;
        return item.label.indexOf(query) > -1;
      },
      //消息对话框
      msgDialogOpen: false,
      //消息对画框标题
      msgDialogTitle: '',
      //要发送的文本消息
      groupTextMsg: '',
      //要发送的群号
      sendGroupUin: null,
      //临时图片地址
      imgUrl: '',
      //消息列表查询参数
      queryMsgParams: {
        pageNum: 1,
        pageSize: 10,
        groupUin: null
      },
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
      listGroup(this.queryParams).then(response => {
        this.groupList = response.rows;
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
    /** 多选框选中数据 */
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
      this.$modal.loading("正在刷新中");
      refreshGroup().then(response => {
        if (response.code === 200) {
          this.$modal.msgSuccess("强制刷新成功");
          this.$modal.closeLoading();
          this.getList();
        } else {
          this.$modal.msgError("强制刷新失败");
        }
      });
    },
    /** 显示群聊插件对话框 */
    handleGroupPlugin(row) {
      this.resetGroupPluginForm();
      //得到群号
      const groupCode = row.groupCode || this.ids
      //得到群名
      const groupName = row.groupName
      this.groupPluginForm = {
        groupCode,
        groupName
      }
      //请求数据
      listGroupPlugin(groupCode).then(response => {
        const groupPlugins = response.rows;
        listPlugin().then(re => {
          this.groupPluginOpen = true;
          const data = []
          re.rows.forEach((plugin, index) => {
            data.push({
              label: plugin.name,
              key: index,
              p: plugin
            })
            groupPlugins.forEach((groupPlugin) => {
              if (plugin.id === groupPlugin.pluginId) {
                this.groupPluginValue.push(index);
              }
            })
          })
          this.groupPluginData = data;
        })
      });
    },
    /** 提交群聊插件信息 */
    submitGroupPluginForm() {
      this.$refs["groupPluginForm"].validate(valid => {
        if (valid) {
          // if (this.form.groupCode != null) {
          //   updateGroup(this.form).then(response => {
          //     this.$modal.msgSuccess("修改成功");
          //     this.open = false;
          //     this.getList();
          //   });
          // }
          //构造数据
          const pluginIds = []
          this.groupPluginValue.forEach((id => {
            pluginIds.push(this.groupPluginData[id].p.id)
          }))
          this.groupPluginForm.pluginIds = pluginIds;
          addGroupPlugin(this.groupPluginForm).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.groupPluginOpen = false;
          })
        }
      });
    },
    /** 取消按钮 */
    cancelGroupPlugin() {
      this.groupPluginOpen = false;
      this.resetGroupPluginForm()
    },
    /** 重置群组插件的表单 */
    resetGroupPluginForm() {
      //表单重置
      this.groupPluginForm = {
        groupCode: null,
        groupName: null,
        pluginIds: null
      };
      this.groupPluginValue = [];
      this.resetForm("groupPluginForm");
    },
    /** 显示消息对话框 */
    handleSendMsg(row) {
      this.msgDialogOpen = true;
      this.msgDialogTitle = row.groupName;
      this.sendGroupUin = row.groupCode;

      // this.$prompt('请输入要发给群"' + row.groupName + '"的文本消息', "提示", {
      //   confirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   closeOnClickModal: false
      // }).then(({ value }) => {
      //   sendGroupText(row.groupCode, value,null).then(response => {
      //     this.$modal.msgSuccess("发送成功");
      //   });
      // }).catch(() => {});
    },
    /** 发送消息 */
    handleSend: function () {
      // console.log(message, next, file);
      console.log(this.groupTextMsg);
      console.log(this.sendGroupUin);
      console.log(this.fileImageList);

      // 去除data:image/png;base64,
      // var dataUrl = this.imgUrl.replace("data:image/png;base64,", "")
      // sendGroupPic(this.sendGroupUin, this.groupTextMsg, dataUrl).then(response => {
      //   if (response.code === 200) {
      //     this.$modal.msgSuccess("发送成功");
      //   } else {
      //     this.$modal.msgSuccess(response.msg);
      //   }
      // })
      sendGroupText(this.sendGroupUin, this.groupTextMsg).then(response => {
        if (response.code === 200) {
          this.$modal.msgSuccess("发送成功");
        } else {
          this.$modal.msgSuccess(response.msg);
        }
        this.groupTextMsg = '';
      });
      // if (message.type === 'text') {
      //   sendGroupText(message.toContactId, message.content).then(response => {
      //     if (response.code === 200) {
      //       next({
      //         status: "succeed",
      //       });
      //     } else {
      //       next({
      //         status: "failed",
      //       });
      //     }
      //   });
      // } else if (message.type === 'image') {
      //   urlToBase64(message.content).then((dataUrl) => {
      //
      //   })
      // }
    },
    handleGroupImage(e) {
      let file = e.target.files[0]

      let reader = new FileReader()

      // if (file.type == 'image/jpg') {
      //
      //   this.$message.error('上传头像图片只能是jpg格式!');
      //
      //   this.$refs['file'].value = ''
      //
      //   return
      //
      // }

      reader.readAsDataURL(file) //将结果转为url，使得img的src可以使用

      reader.onload = (e) => {

        this.imgUrl = e.target.result;//转为url后的结果赋给img的src

      }
    }
  }
};
</script>
