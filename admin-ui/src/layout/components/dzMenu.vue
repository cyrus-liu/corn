<template>
  <el-menu
      class="side-nav-bar"
      router
      :collapse="this.$store.state.collapse"
      :default-active="this.$route.path"
      background-color="#304156"
      text-color="#BFCBD9"
      active-text-color="#409EFF">

    <template>

      <template v-for="route in this.$store.state.userMenuList">
        <template  v-if="route.name && route.children">
          <!-- 二级菜单 -->
          <el-submenu :key="route.path" :index="route.path">
            <!-- 二级菜单标题-->
            <template slot="title">
              <i :class="route.icon"></i>
              <span>{{ route.name }}</span>
            </template>

            <!-- 二级菜单选项-->
            <template v-for="(item,index) in route.children">
              <el-menu-item   :key="index" :index="item.path">
                <i :class="item.icon"></i>
                <span>{{ item.name }}</span>
              </el-menu-item>
            </template>

          </el-submenu>
        </template>

        <!-- 一级菜单 -->
        <template v-else-if="route.children">
          <el-menu-item :index="route.path " :key="route.path">
            <i :class="route.children[0].icon"></i>
            <span>{{ route.children[0].name }}</span>
          </el-menu-item>
        </template>

      </template>

    </template>
  </el-menu>
</template>

<script>
export default {
  name: "dzMenu",
  data() {
    return {}
  },

  created() {

  },

  methods: {}
}
</script>

<style scoped lang="less">
.side-nav-bar:not(.el-menu--collapse) {
  width: 210px;
}

.side-nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  overflow-x: hidden;
  overflow-y: auto;
}

.side-nav-bar i {
  margin-right: 1rem;
}

</style>