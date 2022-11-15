<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>

    <!-- 功能栏-->
    <div class="operation-container">

      <!-- 数据筛选 -->
      <div style="margin-left:auto">
        <el-input
            v-model="queryParams.keywords"
            prefix-icon="el-icon-search"
            size="small"
            placeholder="请输入微信昵称"
            style="width:200px"
            clearable
            @clear="getWxUserData"/>
        <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            style="margin-left:1rem"
            @click="getWxUserData">
          搜索
        </el-button>
      </div>
    </div>

    <!-- 数据表格-->
    <el-table
        :data="userTableData"
        :header-cell-style="{background:'#eef0f1'}"
        style="width: 100%">


      <el-table-column label="OpenId" prop="openId" align="center"/>

      <el-table-column
          label="微信头像"
          prop="avatarUrl"
          align="center"
      >

        <template slot-scope="scope">
          <el-avatar :size="55" :src="scope.row.avatarUrl"></el-avatar>
        </template>
      </el-table-column>

      <el-table-column
          label="微信昵称"
          prop="nickName"
          align="center"
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

    </el-table>

    <!--    分页组件-->
    <el-pagination
        class="pagination-container"
        :page-size.sync="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-sizes="[10, 15, 20, 50]"
        :current-page.sync="queryParams.pageNum"
        @current-change="getWxUserData"
        @size-change="getWxUserData"
    />
  </el-card>
</template>

<script>
import {getWxUserDataList, updateWxUser} from "@/api/wxUser";

export default {
  name: "WxUser",
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
      },
      //总页数
      total: 0,
      //表格数据
      userTableData: []
    }
  },

  created() {
    this.getWxUserData()
  },

  methods: {
    //获取数据
    async getWxUserData() {
      const {data: res} = await getWxUserDataList(this.queryParams)
      this.userTableData = res.data.rows
      this.total = Number(res.data.total)
    },

    //修改用户状态
    async handleStatusChange(row) {
      await updateWxUser(row)
      this.$notify({
        title: '成功',
        message: '修改用户成功',
        type: 'success'
      });
      await this.getWxUserData()
    },
  }
}
</script>

<style scoped lang="less">

</style>