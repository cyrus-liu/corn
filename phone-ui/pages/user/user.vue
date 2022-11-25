<template>
  <view>
    <view class="top">
      <!-- 用户信息 -->
      <view class="user_info" v-if="token">
        <image class="center_img" :src="logo"></image>
        <view style="margin-top: 30rpx; font-size: 22px; color: #F0F3BD;">{{nickName}}</view>
      </view>

      <!-- 登录按钮 -->
      <view style="width: 220rpx;" v-else>
        <u-button type="primary" @click="login" color="linear-gradient(to right, rgb(66, 83, 216), rgb(213, 51, 186))"
          text="登录">
        </u-button>
      </view>

      <!-- 波浪图片 -->
      <image src='https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/bg_wave.gif' mode='scaleToFill'
        class='gif-wave'></image>
    </view>

    <!-- 功能栏 -->
    <view class="d-card">
      <u-cell-group :border="false">
        <u-cell icon="edit-pen" title="花卉识别" @click="goRecordPage" isLink :border="false" v-if="token"></u-cell>
        <u-cell icon="order" title="我的识别记录" @click="goMyRecordPage" isLink :border="false" v-if="token"></u-cell>
        <u-cell icon="question-circle" title="关于我们" isLink :border="false"></u-cell>
        <u-cell icon="lock-open" title="退出登录" @click="show = true" isLink :border="false" v-if="token"></u-cell>
      </u-cell-group>

      <u-divider text="大只Web技术支持" textColor="#eeb154"
        lineColor="#eeb154" textSize="28rpx"></u-divider>
      <view style="width: 160rpx; height: 50rpx; margin: 0 auto;">
        <text>版本号 1.08</text>
      </view>
    </view>


    <!-- 退出提示框 -->
    <u-modal :show="show" :title="title" :content='content' @confirm="logOut" @cancel="show = false" showCancelButton>
    </u-modal>

  </view>
</template>

<script>
  export default {
    data() {
      return {
        logo: 'https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/logo.jpg',
        token: null,
        nickName: null,
        show: false,
        title: '退出登录',
        content: '此操作将退出登录！！！'
      };
    },
    methods: {
      // 获取微信用户的基本信息
      login() {
        var than = this
        wx.login({
          success(res) {
            if (res.code) {
              //发起网络请求
              //向服务器发起登录请求
              uni.$http.post('/wxUser/login', {
                code: res.code
              }).then(res => {

                than.token = res.data.data.token
                than.nickName = res.data.data.nickName

                than.$store.commit('setToken', res.data.data.token)
                than.$store.commit('setNickName', res.data.data.nickName)
                than.token = res.data.data
              })

            } else {
              uni.$u.toast('登录失败：' + res.errMsg)
            }
          }
        })
      },

      //退出登录
      async logOut() {
        //发起退出登录请求
        await uni.$http.get('/wxUser/logout')
        this.token = null
        uni.removeStorageSync('token')
        this.show = false
      },

      //前往取样记录页
      goRecordPage() {
        uni.navigateTo({
          url: "/subpkg/record/record"
        })
      },

      //前往我的记录
      goMyRecordPage() {
        uni.navigateTo({
          url: "/subpkg/myRecord/myRecord"
        })
      }
    },

    onShow() {
      this.token = uni.getStorageSync('token')
      this.nickName = uni.getStorageSync('nickName')
    }
  }
</script>

<style lang="scss">
  Page {
    font-size: 28rpx;
    background: #f9f9fb;
  }

  .d-card {
    z-index: 999;
    position: absolute;
    top: 60%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 85%;
    padding: 30rpx;
    border-radius: 24rpx;
    background-color: #fff;
    box-shadow: 0 8rpx 16rpx 0 rgba(0, 0, 0, 0.2), 0 12rpx 40rpx 0 rgba(0, 0, 0, 0.19);
  }

  .top {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 350rpx;
    background: #00A896;
    padding-top: 30rpx;
    position: relative;

    .user_info {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }

  .center_img {
    width: 140rpx;
    height: 140rpx;
    border-radius: 50%;
    overflow: hidden;
    background-color: #ccc;
  }

  .gif-wave {
    position: absolute;
    width: 100%;
    bottom: 0;
    left: 0;
    z-index: 99;
    mix-blend-mode: screen;
    height: 100rpx;
  }
</style>
