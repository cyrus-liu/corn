import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
  state: {
   latitude: 39.916527, //纬度
   longitude: 116.397128, //经度
  },
  mutations: {
    setLatitude(state, value) {
      state.latitude = value
    },
    setLongitude(state, value) {
      state.longitude = value
    }
  },
  actions: {},
  getters: {}
})
