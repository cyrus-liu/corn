<template>
  <view>
    <image style="width: 100%; height: 480rpx; background-color: #eeeeee;" mode="scaleToFill" :src="recordInfo.imgUrl" />

    <view style="padding: 30rpx;">
      <view class="che">
        样本编号：
        <text style="color: #37ad70;">{{recordInfo.id}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        样本名称：
        <text style="color: #37ad70;">{{recordInfo.name}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        样本备注：
         <text style="color: #37ad70;">{{recordInfo.remark}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        经度：
        <text style="color: #37ad70;">{{recordInfo.longitude}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        纬度：
        <text style="color: #37ad70;">{{recordInfo.latitude}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        检测结果：
        <text style="color: #37ad70;">{{recordInfo.resultName}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        判别概率：
         <!-- Number(recordInfo.resultValue*100).toFixed(1) -->
         <text style="color: #37ad70;">{{recordInfo.resultValue * 100}}%</text>
      </view>
      <u-line color="#2979ff"></u-line>
      
      <view class="che">
        取样日期：
         <text style="color: #37ad70;">{{recordInfo.createTime}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
    </view>


  </view>
</template>

<script>
  export default {
    data() {
      return {
        recordInfo: null
      }
    },
    methods: {
     async  getRecordInfo(id) {
       const {data:res} = await uni.$http.get('/record', {id: id})
       this.recordInfo = res.data
      }
    },

    onLoad(option) {
      this.getRecordInfo(option.id)
    }

  }
</script>

<style>
  .che {
    margin: 15rpx;
  }
</style>
