<template>
  <view>
    <view class="map-container">
      <map style="width: 100%; height: 92vh;" :show-location='true' ref="map" id="map" :latitude="latitude"
        :longitude="longitude" :markers="marker" :scale="scale" @markertap="markertap">
        <view class="view">

          <view style="display: flex; align-items: center; justify-content: center; flex-direction: column;"
            @click="onControltap">
            <image class="cover-image" :src="iocn"></image>
            <view>定位</view>
          </view>
        </view>
      </map>

      <!-- 功能卡片 -->
      <view class="multiFun" v-show="multiFunisShow">
        <view class="line"></view>
        <view style="margin: 20rpx;">
          <u-search placeholder="请输入位置关键词" v-model="keyword" @custom="query"></u-search>
        </view>
      </view>
    </view>

    <u-popup :show="show" @close="close">
      <view class="container">
        <view v-for="(item, index) in queryLocation" :key="index">
          <view class='text-content' @click="doLocation(item.location)">{{item.title}}</view>
        </view>
      </view>
    </u-popup>
  </view>
</template>

<script>
  var QQMapWX = require('../../utils/qqmap-wx-jssdk.min.js');
  var qqmapsdk;

  export default {
    data() {
      return {
        iocn: 'https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/locating.png',
        multiFunisShow: true,
        show: false,
        keyword: '',
        latitude: 39.916527, //纬度
        longitude: 116.397128, //经度
        scale: 12, //缩放级别
        marker: [],
        queryLocation: [],
        isLocated: false // 是否定位成功
      }
    },
    onReady() {},
    computed: {},
    onLoad() {
      // 实例化API核心类
      qqmapsdk = new QQMapWX({
        key: ''
      })

      this.getLocationApi()
      this.getRecordDatas()
    },
    onHide() {
      this.$store.commit('setLatitude', this.latitude);
      this.$store.commit('setLongitude', this.longitude);
    },

    methods: {
      //获取事件坐标
      async getRecordDatas() {
        const {
          data: res
        } = await uni.$http.get('/record/list')

        if (res.code == 200) {
          let newArr = []
          if (res.data.length > 0) {
            res.data.forEach(item => {
              let o = {
                id: item.id,
                longitude: item.longitude, //经度
                latitude: item.latitude, //纬度
                iconPath: '/static/home/Path.png', //显示的图标        
                rotate: 0, // 旋转度数
                width: 33, //宽
                height: 33, //高
              }
              item = o
              newArr.push(item)
            })
          }
          this.marker = newArr
        }
      },

      //清空搜索框
      close() {
        this.multiFunisShow = !this.multiFunisShow
        this.show = !this.show
        this.keyword = ''
        this.queryLocation = []
      },

      // 获取经纬度
      getLocationApi() {
        let that = this;
        uni.getLocation({
          type: "gcj02",
          altitude: false, // 是否返回高度
          accuracy: 'best', // 精度值为20m
          success: (res) => {
            //授权成功后，调用
            //console.log(res, "当前位置的经纬度");
            this.latitude = res.latitude
            this.longitude = res.longitude
          },
          fail(err) {
            //授权失败后，调用，可以在这儿调用下面的例子 再次进行授权

            if (err.errMsg === 'getLocation:fail auth deny') {
              // 未授权
              that.openAuthSetting();
            }

            if (err.errMsg === 'getLocation:fail:ERROR_NOCELL&WIFI_LOCATIONSWITCHOFF') {
              uni.showModal({
                content: '请开启手机定位服务',
                showCancel: false
              })
            }

          },
        });
      },

      openAuthSetting() {
        let that = this;
        uni.getSetting({
          success: (res) => {
            if (
              res.authSetting["scope.userLocation"] != undefined &&
              res.authSetting["scope.userLocation"] != true
            ) {
              uni.showModal({
                title: "请求授权当前位置",
                content: "需要获取您的地理位置，请确认授权",
                success: function(res) {
                  if (res.cancel) {
                    uni.showToast({
                      title: "拒绝授权",
                      icon: "none"
                    });
                  } else if (res.confirm) {
                    uni.openSetting({
                      success: function(dataAu) {
                        if (dataAu.authSetting["scope.userLocation"] == true) {
                          uni.showToast({
                            title: "授权成功",
                            icon: "none"
                          });
                          //再次授权，调用wx.getLocation的API
                          that.getLocationApi();
                        } else {
                          uni.showToast({
                            title: "授权失败",
                            icon: "none"
                          });
                        }
                      },
                    });
                  }
                },
              });
            } else if (res.authSetting["scope.userLocation"] == undefined) {
              //调用wx.getLocation的API
              this.getLocation();
            } else {
              //调用wx.getLocation的API
              this.getLocation();
            }
          },
          complete() {

          },
        });
      },






      //地图搜索功能
      query() {
        //判断非空
        if (!this.keyword) {
          //为空
          return
        }
        let loc = [this.latitude, this.longitude]
        qqmapsdk.search({
          keyword: this.keyword, //搜索关键词
          location: loc.toString(), //设置周边搜索中心点
          success: res => { //搜索成功后的回调
            if (res.data.length > 0) {
              this.multiFunisShow = !this.multiFunisShow
              this.show = !this.show
              this.queryLocation = res.data
            } else {
              uni.showToast({
                title: '什么也没搜索到',
                icon: 'none'
              });
            }
          },
          fail: res => {
            console.log(res);
          }
        })
      },

      //点击搜索出来的位置跳转
      doLocation(location) {
        this.latitude = location.lat
        this.longitude = location.lng
        this.close()
      },

      //定位按钮
      onControltap() {
        this.getLocationApi()
        //moveToLocation将地图中心移动到当前定位点，需要配合map组件的show-location使用
        uni.createMapContext("map", this).moveToLocation({
          latitude: this.latitude,
          longitude: this.longitude,
        });

        this.getRecordDatas()
      },

      //地图点击事件
      markertap(e) {
        uni.navigateTo({
          url: "/subpkg/recordInfo/recordInfo?id=" + e.detail.markerId
        })
      }

    },
  }
</script>

<style scoped lang="scss">
  // 地图
  .map-container {
    margin-top: -40rpx;
    position: relative;
    overflow: hidden;
    border-radius: 50rpx 50rpx 0 0;

    .view {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 10rpx 10rpx;
      color: #4F575F;
      font-weight: 400;
      background-color: #fff;
      background-size: 120rpx 120rpx;
      background-position: center center;
      position: absolute;
      top: 150rpx;
      right: 32rpx;
      border-radius: 15rpx;
    }

    .cover-image {
      display: inline-block;
      width: 70rpx;
      height: 70rpx;
      margin-bottom: 12rpx;
    }

    .multiFun {
      width: 100%;
      border-radius: 25rpx;
    }

    .line {
      width: 100%;
      height: 1.5px;
      background: #7e7e7e;
      position: relative;
      width: 125rpx;
      display: block;
      box-sizing: border-box;
      left: 310rpx;
      top: 15rpx;
      height: 10rpx;
      margin-bottom: 34rpx;
    }
  }

  // 定义.container设置容器样式
  .container {
    padding: 10px 20px;
    align-content: center;

    // 定义.text-content设置文本样式
    .text-content {
      padding: 10px;
      align-content: center;
      border-bottom: 1rpx solid #F5F5F5;
      background-color: #FFFFFF
    }
  }
</style>
