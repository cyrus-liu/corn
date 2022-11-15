<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>

    <!-- 功能栏-->
    <div class="operation-container">
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
            placeholder="请输入取样记录名"
            style="width:200px"
            clearable
            @clear="getRecordData"/>
        <el-button
            type="primary"
            size="small"
            icon="el-icon-search"
            style="margin-left:1rem"
            @click="getRecordData">
          搜索
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import {deleteUser, getUserList} from "@/api/user";

export default {
  name: "Record",
  data() {
    return {
      //表格数据
      recordTableData:[],
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
      },
    }
  },

  created() {

  },

  methods: {
    //获取记录数据
    async getRecordData(){
      const {data: res} = await getRecordDataList(this.queryParams)
      this.recordTableData = res.data.rows
      this.total = Number(res.data.total)
    },

    //删除按钮操作
    async handleDelete(row) {
      const ids = row.id || this.ids

      try {
        const del = await this.$confirm('是否确认删除记录ID为"' + ids + '"的数据项？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        if (del == 'confirm') {
          // await deleteUser(ids)

          // await this.getUsers()
          this.$notify({
            title: '成功',
            message: '删除记录成功',
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
  }
}
</script>

<style scoped lang="less">

</style>