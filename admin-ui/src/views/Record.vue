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

        <el-select v-model="queryParams.status" style="margin-left: 10px" size="small" placeholder="请选择审核状态"
                   clearable>
          <el-option :key="'0'" label="通过" :value="'0'"/>
          <el-option :key="'1'" label="不通过" :value="'1'"/>
          <el-option :key="'2'" label="审核中" :value="'2'"/>
        </el-select>

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


    <!-- 数据表格-->
    <el-table
        :data="recordTableData"
        :header-cell-style="{background:'#eef0f1'}"
        @selection-change="handleSelectionChange"
        style="width: 100%">

      <el-table-column type="selection" width="55" align="center"/>

      <el-table-column label="记录id" prop="id" align="center"/>

      <el-table-column
          label="记录名"
          prop="name"
          align="center"
          :show-overflow-tooltip="true"
      />

      <el-table-column
          label="备注"
          prop="remark"
          align="center"
          :show-overflow-tooltip="true"
      />

      <el-table-column
          label="图片"
          align="center"
      >

        <template slot-scope="scope">
          <el-image
              style="width: 88px; height: 88px"
              :src="scope.row.imgUrl"
              ref="preview"
              @click="imgMagnify(scope.row.imgUrl)"
              :preview-src-list="srcList"
              fit="contain"/>
        </template>

      </el-table-column>

      <el-table-column
          label="取样结果"
          prop="resultName"
          align="center"
          :show-overflow-tooltip="true"
      />

      <el-table-column
          label="经度"
          prop="longitude"
          align="center"
      />

      <el-table-column
          label="纬度"
          prop="latitude"
          align="center"
      />

      <el-table-column
          label="创建者openId"
          prop="createBy"
          align="center"
      />

      <el-table-column
          label="状态"
          align="center"
      >

        <template slot-scope="scope">
          <el-tag v-if="scope.row.status==0" type="success">通过</el-tag>
          <el-tag v-if="scope.row.status==1" type="danger">不通过</el-tag>
          <el-tag v-if="scope.row.status==2" type="warning">审核中</el-tag>
        </template>
      </el-table-column>


      <el-table-column prop="createTime" label="创建时间" align="center"/>

      <el-table-column label="操作" align="center" width="180">
        <template slot-scope="scope">

          <el-button type="text" @click="handleUpdate(scope.row,'0')" v-if="scope.row.status == '2'">
            通过
          </el-button>

          <el-button type="text" @click="handleUpdate(scope.row,'1')" v-if="scope.row.status == '2'">
            不通过
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
        @current-change="getRecordData"
        @size-change="getRecordData"
    />
  </el-card>
</template>

<script>
import {deleteRecord, getRecordDataList, updateRecord} from "@/api/record";
import {updateUser} from "@/api/user";

export default {
  name: "Record",
  data() {
    return {
      //表格数据
      recordTableData: [],
      // 非多个禁用
      multiple: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keywords: '',
        status: ''
      },
      //总页数
      total: 0,
      srcList: []
    }
  },

  created() {
    this.getRecordData()
  },

  methods: {
    //获取记录数据
    async getRecordData() {
      const {data: res} = await getRecordDataList(this.queryParams)
      this.recordTableData = res.data.rows
      this.total = Number(res.data.total)
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.multiple = !selection.length
    },

    async handleUpdate(row, status) {
      row.status = status
      await updateRecord(row)

      this.$notify({
        title: '成功',
        message: '审核成功',
        type: 'success'
      });
      await this.getRecordData()
    },


    //方法图片
    imgMagnify(img) {
      if (this.srcList.length > 0) {
        this.srcList.length = 0
      }
      this.srcList.push(img)
    },

    //删除按钮操作
    async handleDelete(row) {
      const ids = row.id || this.ids

      try {
        const del = await this.$confirm('是否确认删除取样记录ID为"' + ids + '"的数据项？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        if (del == 'confirm') {
          await deleteRecord(ids)

          await this.getRecordData()
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