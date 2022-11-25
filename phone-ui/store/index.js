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
    //用户昵称
    nickName:null

  },
  mutations: {
    setLatitude(state, value) {
      state.latitude = value
    },
    setLongitude(state, value) {
      state.longitude = value
    },
    setToken(state, value) {
      state.token = value,
      uni.setStorageSync('token', value)
    },
    setNickName(state, value) {
      state.nickName = value,
      uni.setStorageSync('nickName', value)
    },

  },
  actions: {},
  getters: {}
})
