<template>
  <div>
    <!-- 导航栏 -->
    <div class="nav-bar">
      <!-- 折叠按钮 -->
      <div @click="trigger" class="hambuger-button">
        <i :class="isFold"/>
      </div>

      <!-- 面包屑导航-->
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.path">
          <span v-if="item.redirect">{{ item.name }}</span>
          <router-link v-else :to="item.path">{{ item.name }}</router-link>
        </el-breadcrumb-item>
      </el-breadcrumb>

    </div>

    <!-- 历史标签栏 -->
    <div class="tabs-view-container">
      <div class="tabs-wrapper">
         <span
             :class="isActive(item)"
             v-for="item in this.$store.state.tabList"
             :key="item.path"
             @click="goTo(item)">
                {{ item.name }}
                <i
                    class="el-icon-close"
                    v-if="item.path !== '/home'"
                    @click.stop="removeTab(item)"/>
         </span>
      </div>

      <div class="tabs-close-item" style="float:right" @click="closeAllTab">
        全部关闭
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TotNav",
  data() {
    return {
      breadcrumbList: []
    }
  },
  created() {
    //替换面包屑导航
    this.breadcrumbList = this.$route.matched.filter(item => item.name);

    //保存当前页标签
    this.$store.commit("setTab", this.$route);
  },
  methods: {
    //跳转标签
    goTo(tab) {
      if (tab.path === this.$route.path) return
      this.$router.push({path: tab.path});
    },

    //删除标签
    removeTab(tab) {
      this.$store.commit("removeTab", tab);
      //如果删除的是当前页则返回上一标签页
      if (tab.path === this.$route.path) {
        let tabList = this.$store.state.tabList;
        this.$router.push({path: tabList[tabList.length - 1].path});
      }
    },

    //删除全部标签
    closeAllTab() {
      this.$store.commit("resetTab");
      if (this.$route.path === "/home") return
      this.$router.push({path: "/home"});
    },

    //修改左边菜单的闭合状态
    trigger() {
      this.$store.commit("setCollapse");
    }
  },
  computed: {
    isFold() {
      return this.$store.state.collapse ? "el-icon-s-unfold" : "el-icon-s-fold";
    },
    //标签是否处于当前页
    isActive() {
      return function (tab) {
        if (tab.path === this.$route.path) {
          return "tabs-view-item-active";
        }
        return "tabs-view-item";
      };
    },
  },
}
</script>

<style scoped lang="less">
.nav-bar {
  display: flex;
  align-items: center;
  padding-left: 15px;
  padding-right: 30px;
  height: 50px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.hambuger-button {
  font-size: 1.25rem;
  cursor: pointer;
  margin-right: 24px;
}

.tabs-wrapper {
  overflow-x: auto;
  overflow-y: hidden;
  white-space: nowrap;
  width: 95%;
}

.tabs-view-container {
  display: flex;
  position: relative;
  padding-left: 10px;
  padding-right: 10px;
  height: 33px;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
}

.tabs-view-item {
  display: inline-block;
  cursor: pointer;
  height: 25px;
  line-height: 25px;
  border: 1px solid #d8dce5;
  color: #495060;
  background: #fff;
  padding: 0 8px;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
}

.tabs-close-item {
  position: absolute;
  right: 10px;
  display: inline-block;
  cursor: pointer;
  height: 25px;
  line-height: 25px;
  border: 1px solid #d8dce5;
  color: #495060;
  background: #fff;
  padding: 0 8px;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
}

.tabs-view-item-active {
  display: inline-block;
  cursor: pointer;
  height: 26px;
  line-height: 26px;
  padding: 0 8px;
  font-size: 12px;
  margin-top: 4px;
  margin-left: 5px;
  background-color: #42b983;
  color: #fff;

  border-color: #42b983;
}

.tabs-view-item-active:before {
  content: "";
  background: #fff;
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  position: relative;
  margin-right: 2px;
}


</style>