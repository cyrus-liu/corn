<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import {getUserMenuList} from '@/api/menu';

export default {
  created() {
    /**
     * 刷新导致vue组件的生命周期重启
     * 判断用户是否登录状态
     * 然后刷新页面查询用户菜单
     */
    if (this.$store.state.token != null) {
      getUserMenuList()
    }
  }
};
</script>



