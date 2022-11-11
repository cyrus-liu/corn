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


  </el-card>
</template>

<script>
import {getUserList} from "@/api/user";

export default {
  name: "User",
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 非多个禁用
      multiple: true,
      // 选中数组
      ids: [],
      //总页数
      total:0,
      //表格数据
      userTableData:[],
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
  },

  methods: {

    //获取数据
    async getUsers(){
      const {data: res} = await getUserList(this.queryParams)
      this.userTableData = res.data.rows
      this.total =  Number(res.data.total)
    },

    //新增按钮操作
    handleAdd() {
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
      //await updateRole(row)
      this.$notify({
        title: '成功',
        message: '修改用户成功',
        type: 'success'
      });
      await this.getUsers()
    },

    //修改角色
    async handleUpdate(row) {
      // this.reset()
      //
      // const {data: res} = await getRoleBy({id: row.id})
      // this.form = res.data
      // this.open = true
      // this.$nextTick(async () => {
      //   const {data: res} = await getRoleMenuIds({id: row.id})
      //   res.data.forEach(value => {
      //     this.$refs.menu.setChecked(value, true, false);
      //   })
      // })
      // this.title = '修改角色'
    },

    //删除按钮操作
    async handleDelete(row) {
      // const ids = row.id || this.ids
      //
      // try {
      //   const del = await this.$confirm('是否确认删除角色ID为"' + ids + '"的数据项？', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   })
      //
      //   if (del == 'confirm') {
      //     await deleteRole(ids)
      //     await this.getRoles()
      //     this.$notify({
      //       title: '成功',
      //       message: '删除角色成功',
      //       type: 'success'
      //     });
      //   }
      //
      // } catch (e) {
      //   this.$message({
      //     type: 'info',
      //     message: '已取消删除'
      //   });
      // }
    },
  }
}
</script>

<style scoped lang="less">

</style>