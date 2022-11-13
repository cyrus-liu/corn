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
        新增角色
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
            placeholder="请输入角色名"
            style="width:200px"
            clearable
            @clear="getRoles"/>
        <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            style="margin-left:1rem"
            @click="getRoles">
          搜索
        </el-button>
      </div>
    </div>

    <!-- 数据表格-->
    <el-table
        :data="roleTableData"
        :header-cell-style="{background:'#eef0f1'}"
        @selection-change="handleSelectionChange"
        style="width: 100%">

      <el-table-column type="selection" width="55" align="center"/>

      <el-table-column label="角色id" prop="id" align="center"/>
      <el-table-column
          label="角色名称"
          prop="roleName"
          align="center"
          :show-overflow-tooltip="true"
      />

      <el-table-column
          label="权限字符"
          prop="roleKey"
          align="center"
          :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <el-tag type="danger">{{scope.row.roleKey}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="显示顺序" prop="roleSort" align="center"/>

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
        @current-change="getRoles"
        @size-change="getRoles"
    />

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称"/>
        </el-form-item>

        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip
                content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)"
                placement="top"
            >
              <i class="el-icon-question"/>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="form.roleKey" placeholder="请输入权限字符"/>
        </el-form-item>

        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number
              v-model="form.roleSort"
              controls-position="right"
              :min="0"
          />
        </el-form-item>

        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio :key="'0'" :label="'0'">正常</el-radio>
            <el-radio :key="'1'" :label="'1'">停用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="菜单权限">
          <el-checkbox
              v-model="menuExpand"
              @change="handleCheckedTreeExpand($event)"
          >展开/折叠
          </el-checkbox>
          <el-checkbox
              v-model="menuNodeAll"
              @change="handleCheckedTreeNodeAll($event)"
          >全选/全不选
          </el-checkbox>

          <el-tree
              ref="menu"
              class="tree-border"
              :data="menuOptions"
              show-checkbox
              node-key="id"
              :check-strictly="false"
              empty-text="加载中，请稍候"
              :props="defaultProps"
          />

        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </el-card>
</template>

<script>
import {addRole, deleteRole, getRoleBy, getRoleList, updateRole} from "@/api/role";
import {getMenuList, getRoleMenuIds} from "@/api/menu";
import {createTree} from "@/utils/createTree";

export default {
  name: "Role",
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
      },
      // 总条数
      total: 0,
      // 非多个禁用
      multiple: true,
      //角色表格数据
      roleTableData: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 菜单列表
      menuOptions: [],
      // 选中数组
      ids: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      menuExpand: false,
      menuNodeAll: false,
      // 表单校验
      rules: {
        roleName: [
          {required: true, message: '角色名称不得为空', trigger: 'blur'}
        ],
        roleKey: [
          {required: true, message: '权限标识不得为空', trigger: 'blur'}
        ]
      }

    }
  },

  created() {
    this.getRoles()
  },

  methods: {
    //获取角色列表
    async getRoles() {
      const {data: res} = await getRoleList(this.queryParams)
      this.roleTableData = res.data.rows
      this.total =  Number(res.data.total)
    },

    //新增按钮操作
    handleAdd() {
      this.reset()
      this.getTreeSelect()
      this.open = true
      this.title = '添加角色'
    },

    //查询菜单树结构
    async getTreeSelect() {
      const {data: res} = await getMenuList()
      this.menuOptions = createTree(res.data, 'id')
    },


    //修改角色状态
    async handleStatusChange(row) {
      await updateRole(row)
      this.$notify({
        title: '成功',
        message: '修改角色成功',
        type: 'success'
      });
      await this.getRoles()
    },

    //修改角色
    async handleUpdate(row) {
      this.reset()
      await this.getTreeSelect()
      const {data: res} = await getRoleBy({id: row.id})
      this.form = res.data
      this.open = true
      this.$nextTick(async () => {
        const {data: res} = await getRoleMenuIds({id: row.id})
        res.data.forEach(value => {
          this.$refs.menu.setChecked(value, true, false);
        })
      })
      this.title = '修改角色'
    },

    //提交按钮
    submitForm() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          if (this.form.id !== null) {
            this.form.menuIds = this.getMenuAllCheckedKeys()
            await updateRole(this.form)
            this.$notify({
              title: '成功',
              message: '修改角色成功',
              type: 'success'
            });
            this.open = false
            await this.getRoles()
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys()
            await addRole(this.form)
            this.$notify({
              title: '成功',
              message: '新增角色成功',
              type: 'success'
            });
            this.open = false
            await this.getRoles()
          }
        }
      })
    },


    //删除角色
    async handleDelete(row) {
      const ids = row.id || this.ids

      try {
        const del = await this.$confirm('是否确认删除角色ID为"' + ids + '"的数据项？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        if (del == 'confirm') {
          await deleteRole(ids)
          await this.getRoles()
          this.$notify({
            title: '成功',
            message: '删除角色成功',
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

    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      const checkedKeys = this.$refs.menu.getCheckedKeys()
      // 半选中的菜单节点
      const halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys()
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys)
      return checkedKeys
    },

    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value) {
      const treeList = this.menuOptions
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value) {
      this.$refs.menu.setCheckedNodes(value ? this.menuOptions : [])
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },

    // 表单重置
    reset() {
      if (this.$refs.menu) {
        this.$refs.menu.setCheckedKeys([])
      }
      this.menuNodeAll = false
      this.menuNodeAll = false
      this.menuExpand = false

      this.form = {
        id: null,
        roleName: null,
        roleKey: null,
        roleSort: 0,
        status: '0',
        menuIds: [],
      }

      this.resetForm('form')
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },

    //清空表单
    resetForm(formName) {
      if (this.$refs.form) {
        this.$nextTick(() => {
          this.$refs[formName].resetFields();
        })
      }
    }


  }
}
</script>

<style scoped lang="less">

</style>