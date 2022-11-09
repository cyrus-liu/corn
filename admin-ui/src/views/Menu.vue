<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>
    <!-- 功能栏-->
    <div class="operation-container">
      <el-button
          type="primary"
          size="small"
          icon="el-icon-plus"
          @click="addMenu">
        新增菜单
      </el-button>

      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
            v-model="keywords"
            prefix-icon="el-icon-search"
            size="small"
            placeholder="请输入菜单名"
            style="width:200px"
            clearable
            @clear="getMenus"/>
        <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            style="margin-left:1rem"
            @click="getMenus">
          搜索
        </el-button>
      </div>
    </div>

    <!-- 数据表格-->
    <el-table
        :data="menuTableData"
        :header-cell-style="{background:'#eef0f1'}"
        row-key="id"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        style="width: 100%">

      <!-- 菜单名称 -->
      <el-table-column prop="menuName" :show-overflow-tooltip="true" label="菜单名称"/>

      <!-- 图标-->
      <el-table-column prop="icon" label="图标" align="center">
        <template slot-scope="scope ">
          <svg-icon :icon-class="scope.row.icon" v-if="scope.row.icon" style="width: 20px; height: 20px"/>
          <span v-else>-</span>
        </template>
      </el-table-column>

      <!-- 类型-->
      <el-table-column prop="menuType" label="类型" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.menuType==='M'">目录</el-tag>
          <el-tag type="warning" v-else-if="scope.row.menuType==='C'">菜单</el-tag>
          <el-tag type="danger" v-else-if="scope.row.menuType==='F'">按钮</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="orderNum" label="排序" align="center"/>

      <el-table-column prop="perms" label="权限标识" align="center">
        <template slot-scope="scope">
          {{ scope.row.perms ? scope.row.perms : '-' }}
        </template>
      </el-table-column>

      <el-table-column prop="path" label="路由地址" align="center">
        <template slot-scope="scope">
          {{ scope.row.path ? scope.row.path : '-' }}
        </template>
      </el-table-column>

      <el-table-column prop="component" label="组件地址" align="center">
        <template slot-scope="scope">
          {{ scope.row.component ? scope.row.component : '-' }}
        </template>
      </el-table-column>

      <el-table-column prop="status" label="状态" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0" type="success">正常</el-tag>
          <el-tag v-if="scope.row.status==1" type="danger">停用</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="text" @click="updateMenu(scope.row)">
            修改
          </el-button>

          <el-popconfirm
              title="确定删除吗？"
              style="margin-left:10px"
              @confirm="deleteMenu(scope.row)">
            <el-button type="text" slot="reference" style="color: red">
              删除
            </el-button>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <TreeSelect
                  v-model="form.parentId"
                  :options="menuOptions"
                  :normalizer="normalizer"
                  :show-count="true"
                  placeholder="选择上级菜单"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="form.menuType" @change="menuTypeChange">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-if="form.menuType != 'F'" label="菜单图标">
              <el-select
                  v-model="form.icon"
                  placeholder="图标"
                  clearable
                  size="small"
                  style="width: 240px"
              >
                <el-option v-for="(item, index) in icons" :key="index" :value="item">
                  <svg-icon :icon-class="item"/>
                  <span style="margin-left: 8px">{{ item }}</span>
                </el-option>
              </el-select>

            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="form.menuName" placeholder="请输入菜单名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-if="form.menuType != 'F' && form.menuType != 'M'" prop="path">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`/user`" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                路由地址
              </span>
              <el-input v-model="form.path" placeholder="请输入路由地址"/>
            </el-form-item>
          </el-col>
          <el-col v-if="form.menuType == 'C'" :span="12">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="访问的组件路径，如：`/User`，默认在`views`目录下" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                组件路径
              </span>
              <el-input v-model="form.component" placeholder="请输入组件路径"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'M' && form.menuType != 'C'">
              <el-input v-model="form.perms" placeholder="请输入权限标识" maxlength="100"/>
              <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如："
                            placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                权限字符
              </span>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item v-if="form.menuType != 'F'">
              <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                  <i class="el-icon-question"/>
                </el-tooltip>
                菜单状态
              </span>
              <el-radio-group v-model="form.status">
                <el-radio :key="'0'" :label="'0'">正常</el-radio>
                <el-radio :key="'1'" :label="'1'">停用</el-radio>
              </el-radio-group>
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
import {addMenu, deleteMenu, getMenuBy, getMenuList, updateMenu} from "@/api/menu";
import {createTree} from "@/utils/createTree";
import TreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: "Menu",
  components: {TreeSelect},
  data() {
    return {
      //搜索关键词
      keywords: '',
      //表格数据
      menuTableData: [],
      // 弹出层标题
      title: '',
      // 表单参数
      form: {},
      // 是否显示弹出层
      open: false,
      // 菜单树选项
      menuOptions: [],
      //图标列表
      icons: [],
      // 表单校验
      rules: {
        menuName: [
          {required: true, message: '菜单名称不得为空', trigger: 'blur'}
        ],
        orderNum: [
          {required: true, message: '菜单顺序不得为空', trigger: 'blur'}
        ],
        path: [
          {required: true, message: '路由地址不得为空', trigger: 'blur'}
        ],
        component: [
          {required: true, message: '组件路径不得为空', trigger: 'blur'}
        ]
      }
    }
  },

  created() {
    this.getMenus()
    this.icons = this.getIcons()
  },

  methods: {
    //获取菜单数据
    async getMenus() {
      const {data: res} = await getMenuList({keywords: this.keywords})
      if (res) {
        //创建树型数据赋值给表格数据源
        this.menuTableData = createTree(res.data);
      }
    },

    getIcons() {
      const req = require.context('@/assets/icons/svg', false, /\.svg$/)
      const requireAll = requireContext => requireContext.keys()
      const re = /\.\/(.*)\.svg/
      const arr = requireAll(req).map(i => {
        return i.match(re)[1]
      })
      return arr
    },

    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },

    //转换菜单数据结构
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.id,
        label: node.menuName,
        children: node.children
      }
    },

    //单选框数据改变触发
    menuTypeChange(val) {
      if (val == 'M') {
        this.form.component = null
        this.form.perms = null
        this.form.path = null
      }
      if (val == 'C') {
        this.form.perms = null
      }
      if (val == 'F') {
        this.form.path = null
        this.form.component = null
        this.form.icon = null
        this.form.status = null
      }
    },

    // 表单重置
    reset() {
      this.form = {
        id: null,
        parentId: 0,
        path: null,
        menuName: null,
        icon: null,
        component: null,
        perms: null,
        menuType: 'M',
        orderNum: 0,
        isCache: '0',
        status: '0'
      }
      this.resetForm('form')
    },

    //新增菜单
    addMenu() {
      this.reset()
      this.getTreeSelect()
      this.form.parentId = 0
      this.open = true
      this.title = '添加菜单'
    },
    //查询菜单下拉树结构
    async getTreeSelect() {
      const {data: res} = await getMenuList()
      this.menuOptions = []
      const menu = {id: 0, menuName: '主类目', children: []}
      menu.children = createTree(res.data, 'id')
      this.menuOptions.push(menu)
    },

    //修改菜单
    async updateMenu(row) {
      this.$nextTick(() => {
        this.reset()
      })
      await this.getTreeSelect()
      const {data: res} = await getMenuBy({id: row.id})
      this.form = res.data
      this.open = true
      this.title = '修改菜单'
    },

    //删除菜单
    async deleteMenu(row) {
      if (row.children && row.children.length > 0) {
        this.$notify.error({
          title: '错误',
          message: '存在子菜单，不得删除'
        });
        return
      }

      await deleteMenu({id: row.id})
      this.$notify({
        title: '成功',
        message: '删除菜单成功',
        type: 'success'
      });
      await this.getMenus()
    },

    //提交表单
    submitForm() {
      this.$refs['form'].validate(async valid => {
        if (valid) {
          if (this.form.id !== null) {
            //修改
            await updateMenu(this.form)
            this.$notify({
              title: '成功',
              message: '修改菜单成功',
              type: 'success'
            });

            this.open = false
            await this.getMenus()
          } else {
            //新增
            await addMenu(this.form)
            this.$notify({
              title: '成功',
              message: '新增菜单成功',
              type: 'success'
            });

            this.open = false
            await this.getMenus()
          }
        }
      })
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