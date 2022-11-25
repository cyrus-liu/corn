import App from './App'
import store from './store'

// #ifndef VUE3
import Vue from 'vue'

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App,
  store,
})

import {
  $http
} from '@escook/request-miniprogram'

uni.$http = $http
// 配置请求根路径
$http.baseUrl = 'http://127.0.0.1:8881'
// $http.baseUrl = 'https://www.abinya.top'

// 请求开始之前做一些事情
$http.beforeRequest = function(config) {

  let token = uni.getStorageSync('token')
  if (token) {
    config.header.token = token
  }

  uni.showLoading({
    title: '数据加载中...',
  })
}

// 请求完成之后做一些事情
$http.afterRequest = function(config) {
  
  if (config.data.code == 401){
     uni.removeStorageSync('token')
     uni.removeStorageSync('nickName')
     return uni.$u.toast('登录失效，请重新登录')
  }

  if (config.data.code != 200) return uni.$u.toast(config.data.msg)
  

  uni.hideLoading()
}

app.$mount()
// #endif
import uView from '@/uni_modules/uview-ui'

Vue.use(uView)
// #ifdef VUE3
import {
  createSSRApp
} from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif
