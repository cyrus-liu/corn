<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.name }}</div>

    <div style="margin-top: 60px">
      <el-divider/>
      <h3>架构图</h3>
      <el-image style="width: 900px; height: 600px;" :src="src"></el-image>
      <el-divider/>
    </div>

  </el-card>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      src:'https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/B7CA798E-22B4-47AD-B56F-757FB86715E7.png'
    }
  },

  created() {

  },

  methods: {}
}
</script>

<style scoped lang="less">
</style>