<template>
  <view>
    <view class="d-card" v-for="itme in dataList" :key="itme.id" @click="getInfo(itme.id)">
      <view style="display: flex;flex-direction: column; ">
        <text style="margin-bottom: 10rpx;">名称：{{itme.name}}</text>
        <text style="margin-bottom: 10rpx;">时间：{{itme.createTime}}</text>
      </view>

      <view v-if="itme.status == '0'"
        style="width: 120rpx; height: 120rpx; background-color: #4dc01d; border-radius: 50%; color: #fff; line-height: 120rpx; text-align: center;">
        通过
      </view>

      <view v-if="itme.status == '1'"
        style="width: 120rpx; height: 120rpx; background-color: #f05459; border-radius: 50%; line-height: 120rpx; color: #fff; text-align: center;">
        未通过
      </view>

      <view v-if="itme.status == '2'"
        style="width: 120rpx; height: 120rpx; background-color: #f69f30; border-radius: 50%; line-height: 120rpx; color: #fff; text-align: center;">
        审核中
      </view>
    </view>
    
    <u-divider v-if="dataList.length == 0"  text="空空如也~"></u-divider>

  </view>
</template>

<script>
  export default {
    data() {
      return {
        dataList: []
      }
    },
    methods: {
      async getRecordDatas() {
        const {
          data: res
        } = await uni.$http.get('/record/my')
        this.dataList = res.data
        
      },
      //获取详细信息
      getInfo(id) {
        uni.navigateTo({
          url: "/subpkg/recordInfo/recordInfo?id=" + id
        })
      }

    },
    onShow() {
      this.getRecordDatas()
    },
  }
</script>

<style>
  .d-card {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 85%;
    margin: 30rpx auto;
    padding: 30rpx;
    border-radius: 24rpx;
    background-color: #fff;
    box-shadow: 0 8rpx 16rpx 0 rgba(0, 0, 0, 0.2), 0 12rpx 40rpx 0 rgba(0, 0, 0, 0.19);
  }
</style>
