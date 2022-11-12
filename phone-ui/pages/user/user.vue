<template>
  <view>
    <view class="top">
      <!-- 用户信息 -->
      <view class="user_info" v-if="token">
        <image class="center_img" :src="userinfo.avatarUrl"></image>
        <view style="margin-top: 30rpx; font-size: 22px;">{{userinfo.nickName}}</view>
      </view>

      <!-- 登录按钮 -->
      <view style="width: 220rpx;" v-else>
        <u-button type="primary" @click="login" color="linear-gradient(to right, rgb(66, 83, 216), rgb(213, 51, 186))"
          text="登录">
        </u-button>
      </view>

      <!-- 波浪图片 -->
      <image src='https://codermoyv.gitee.io/coder-moyv/assets/images/wechat/bg_wave.gif' mode='scaleToFill'
        class='gif-wave'></image>
    </view>

    <!-- 功能栏 -->
    <view class="d-card">
      <u-cell-group>
        <u-cell icon="edit-pen" title="取样" url="" isLink :border="false" v-if="token"></u-cell>
        <u-cell icon="order" title="取样记录" url="" isLink :border="false" v-if="token"></u-cell>
        <u-cell icon="question-circle" title="关于" url="" isLink :border="false"></u-cell>
        <u-cell icon="lock-open" title="退出登录" @click="show = true" isLink :border="false" v-if="token"></u-cell>
      </u-cell-group>
    </view>

    <!-- 对话框 -->
    <u-modal :show="show" :title="title" :content='content' @confirm="logOut" @cancel="show = false" showCancelButton>
    </u-modal>

  </view>
</template>

<script>
  export default {
    data() {
      return {
        token: null,
        userinfo: null,
        show: false,
        title: '标题',
        content: '此操作将退出登录！！！'
      };
    },
    methods: {
      // 获取微信用户的基本信息
      login() {
        var t = this
        uni.getUserProfile({
          desc: '你的授权信息',
          success: (res) => {
            // 将信息存到 vuex 中
            this.$store.commit('setUserInfo', res.userInfo);
            this.userinfo = this.$store.state.userInfo
            uni.login({
              success(res) {

                //向服务器发起登录请求
                uni.$http.post('/wxUser/login', {
                  code: res.code,
                  nickName: t.userinfo.nickName,
                  avatarUrl: t.userinfo.avatarUrl
                }).then(res => {
                  console.log(res);
                  if (res.data.code != 200) return uni.$u.toast(res.data.msg)
                  t.$store.commit('setToken', res.data.data);
                  t.token = res.data.data
                })

              }
            })
          },
        })
      },

      //退出登录
      async logOut() {
        this.token = null
        this.userinfo = null
        uni.removeStorageSync('userInfo')
        uni.removeStorageSync('token')
        //发起退出登录请求
        const {
          data: res
        } = await uni.$http.get('/wxUser/logout')
        console.log(res);
        this.show = false

      }
    },

    onShow() {
      this.userinfo = uni.getStorageSync('userInfo')
      this.token = uni.getStorageSync('token')
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
    top: 50%;
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
    width: 70px;
    height: 70px;
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
