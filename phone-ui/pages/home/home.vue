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
          <u-search placeholder="请输入搜索关键词" v-model="keyword" @custom="query"></u-search>
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
        iocn:'https://corn-1306784580.cos.ap-guangzhou.myqcloud.com/locating.png',
        multiFunisShow: true,
        show: false,
        keyword: '',
        latitude: 39.916527, //纬度
        longitude: 116.397128, //经度
        scale: 12, //缩放级别
        marker: [],
        queryLocation: []
      }
    },
    onReady() {},
    computed: {},
    onLoad() {
      // 实例化API核心类
      qqmapsdk = new QQMapWX({
        key: 'APBBZ-OWMW2-FDXUN-CDCI7-H5LIS-EEBNH'
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
        
        console.log(res.data);

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
      
      //获取当前位置信息
      getLocationApi() {
        uni.getLocation({
          type: 'gcj02', // 标准
          success: res => {
            this.latitude = res.latitude
            this.longitude = res.longitude
            
            console.log('当前位置经度：' + res.latitude);
            console.log('当前位置纬度：' + res.longitude);
          },
          fail: () => {
            uni.showToast({
              title: '操作频繁，请稍后重试',
              icon: 'none'
            });
          }
        })
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
