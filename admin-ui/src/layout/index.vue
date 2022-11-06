<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <!--    左边菜单栏-->
    <el-aside width="auto">
      <dzMenu/>
    </el-aside>

    <!--    导航栏-->
    <el-container :class="'main-container ' + isHide">
      <el-header height="84px" style="padding:0">
        <dzNav :key="$route.fullPath"/>
      </el-header>

      <!-- 内容-->
      <el-main style="background:#F7F9FB">
        <div class="fade-transform-box">
          <transition name="fade-transform" mode="out-in">
            <router-view :key="$route.fullPath"/>
          </transition>
        </div>
      </el-main>
    </el-container>


  </el-container>

</template>

<script>
import dzMenu from "@/layout/components/dzMenu";
import dzNav from "@/layout/components/dzNav";

export default {
  name: "layout",
  components: {dzMenu, dzNav},
  data() {

    return {}
  },

  created() {

  },

  methods: {},
  computed: {
    isHide() {
      return this.$store.state.collapse ? "hideSideBar" : "";
    }
  }
}
</script>

<style scoped lang="less">

.main-container {
  transition: margin-left 0.45s;
  margin-left: 210px;
  min-height: 100vh;
}

.hideSideBar {
  margin-left: 64px;
}

.fade-transform-enter-active,
.fade-transform-leave-active {
  transition: all 0.5s ease 0s;
}

.fade-transform-enter {
  opacity: 0;
  transform: translateX(-30px);
}

.fade-transform-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

.fade-transform-box {
  position: relative;
  top: 0px;
  bottom: 0px;
  width: 100%;
  overflow: hidden;
}

</style>