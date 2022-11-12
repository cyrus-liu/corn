import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
  state: {
    //纬度
    latitude: 39.916527,
    //经度
    longitude: 116.397128,
    // 登录成功之后的 token 字符串
    token: null,
    // 用户的基本信息
    userInfo: {}

  },
  mutations: {
    setLatitude(state, value) {
      state.latitude = value
    },
    setLongitude(state, value) {
      state.longitude = value
    },
    // 更新用户的基本信息
    setUserInfo(state, value) {
      state.userInfo = value,
      // 将 userinfo 持久化存储到本地
      uni.setStorageSync('userInfo', value)
    },
    setToken(state, value) {
      state.token = value,
      // 将 userinfo 持久化存储到本地
      uni.setStorageSync('token', value)
    },


  },
  actions: {},
  getters: {}
})
