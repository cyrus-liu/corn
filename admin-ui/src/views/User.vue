<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>

    <!-- 功能栏-->
    <div class="operation-container">
      <el-button
          type="primary"
          size="small"
          icon="el-icon-plus"
          @click="handleAdd">
        新增用户
      </el-button>

      <el-button
          type="danger"
          icon="el-icon-delete"
          size="small"
          :disabled="multiple"
          @click="handleDelete">
        删除
      </el-button>

      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
            v-model="queryParams.keywords"
            prefix-icon="el-icon-search"
            size="small"
            placeholder="请输入用户名"
            style="width:200px"
            clearable
            @clear="getUsers"/>
        <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            style="margin-left:1rem"
            @click="getUsers">
          搜索
        </el-button>
      </div>
    </div>

    <!-- 数据表格-->
    <el-table
        :data="userTableData"
        :header-cell-style="{background:'#eef0f1'}"
        @selection-change="handleSelectionChange"
        style="width: 100%">

      <el-table-column type="selection" width="55" align="center"/>

      <el-table-column label="用户id" prop="id" align="center"/>

      <el-table-column
          label="用户名"
          prop="userName"
          align="center"
          :show-overflow-tooltip="true"
      />

      <el-table-column
          label="名称"
          prop="nickName"
          align="center"
          :show-overflow-tooltip="true"
      />


      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="创建时间" align="center"/>

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="handleUpdate(scope.row)">
            修改
          </el-button>

          <el-button type="text" slot="reference" style="color: red" @click="handleDelete(scope.row)">
            删除
          </el-button>

        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        class="pagination-container"
        :page-size.sync="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-sizes="[10, 15, 20, 50]"
        :current-page.sync="queryParams.pageNum"
        @current-change="getUsers"
        @size-change="getUsers"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-row>
          <el-col :span="24">
            <el-form-item label="昵称" prop="nickName">
              <el-input
                  v-model="form.nickName"
                  placeholder="请输入用户昵称"
                  maxlength="30"
              />
            </el-form-item>
          </el-col>
        </el-row>


        <el-row>
          <el-col :span="12">
            <el-form-item
                v-if="form.id == null"
                label="账号"
                prop="userName"
            >
              <el-input
                  v-model="form.userName"
                  placeholder="请输入账号"
                  maxlength="30"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item
                label="密码"
                prop="password"
            >
              <el-input
                  v-model="form.password"
                  placeholder="请输入密码"
                  type="password"
                  maxlength="20"
                  show-password
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio :key="'0'" :label="'0'">正常</el-radio>
                <el-radio :key="'1'" :label="'1'">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="角色">

              <el-select v-model="form.roleIds" multiple placeholder="请选择">

                <el-option
                    v-for="item in roleOptions"
                    :key="item.id"
                    :label="item.roleName"
                    :value="item.id"
                    :disabled="item.status == 1"
                />
              </el-select>


            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </el-card>
</template>

<script>
import {addUser, deleteUser, getUserBy, getUserList, updateUser} from "@/api/user";
import {getRoleOptions} from "@/api/role";

export default {
  name: "User",
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 非多个禁用
      multiple: true,
      // 弹出层标题
      title: '',
      // 角色选项
      roleOptions: [],
      // 选中数组
      ids: [],
      // 表单参数
      form: {},
      //总页数
      total: 0,
      //表格数据
      userTableData: [],
      // 表单校验
      rules: {
        userName: [
          {required: true, message: '账号不得为空', trigger: 'blur'},
        ],
        nickName: [
          {required: true, message: '用户昵称不得为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不得为空', trigger: 'blur'},
          {
            min: 6,
            max: 18,
            message: '用户密码长度必须介于 5 和 18 之间',
            trigger: 'blur'
          }
        ],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
      },
    }
  },

  created() {
    this.getUsers()
    this.getRoleOptions()
  },

  methods: {

    //获取数据
    async getUsers() {
      const {data: res} = await getUserList(this.queryParams)
      this.userTableData = res.data.rows
      this.total = Number(res.data.total)
    },

    //查询角色列表
    async getRoleOptions() {
      const {data: res} = await getRoleOptions()
      this.roleOptions = res.data
    },

    //新增按钮操作
    async handleAdd() {
      this.reset()

      this.open = true
      this.title = '添加用户'
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },

    //修改用户状态
    async handleStatusChange(row) {
      await updateUser(row)
      this.$notify({
        title: '成功',
        message: '修改用户成功',
        type: 'success'
      });
      await this.getUsers()
    },


    //修改按钮操作
    async handleUpdate(row) {
      this.reset()
      const id = row.id

      //查询用户信息
      const {data: res} = await getUserBy({id: id})
      this.form = res.data
      this.open = true
      this.title = '修改用户'
    },

    //删除按钮操作
    async handleDelete(row) {
      const ids = row.id || this.ids

      try {
        const del = await this.$confirm('是否确认删除用户ID为"' + ids + '"的数据项？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        if (del == 'confirm') {
          await deleteUser(ids)
          console.log(ids)
          await this.getUsers()
          this.$notify({
            title: '成功',
            message: '删除用户成功',
            type: 'success'
          });
        }

      } catch (e) {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      }
    },


    // 取消按钮
    cancel() {
      this.open = false
      // this.reset()
    },

    // 表单重置
    reset() {
      this.form = {
        id: null,
        userName: null,
        nickName: null,
        password: null,
        status: '0',
        roleIds: []
      }
      this.resetForm('form')
    },

    //清空表单
    resetForm(formName) {
      if (this.$refs.form) {
        this.$nextTick(() => {
          this.$refs[formName].resetFields();
        })
      }
    },

    //提交按钮
    submitForm() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          if (this.form.id) {

            await updateUser(this.form)
            this.$notify({
              title: '成功',
              message: '修改用户成功',
              type: 'success'
            });

            this.open = false
            await this.getUsers()

          } else {
            await addUser(this.form)
            this.$notify({
              title: '成功',
              message: '新增用户成功',
              type: 'success'
            });
            this.open = false
            await this.getUsers()
          }
        }
      })
    }
  }
}
</script>

<style scoped lang="less">

</style>