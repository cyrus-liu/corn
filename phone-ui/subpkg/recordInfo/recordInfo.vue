<template>
  <view>
    
    <image v-if="!loading" style="width: 100%; height: 550rpx; background-color: #eeeeee;" mode="scaleToFill"
      :src="recordInfo.imgUrl" />

    <view style="padding: 30rpx;">
      
      <u-skeleton rows="18"  title :loading="loading"></u-skeleton>
      
      <view  v-if="!loading">
        <view class="che">
          编号：
          <text style="color: #37ad70;">{{recordInfo.id}}</text>
        </view>
        <u-line color="#2979ff"></u-line>
        <view class="che">
          名称：
          <text style="color: #37ad70;">{{recordInfo.name}}</text>
        </view>
        <u-line color="#2979ff"></u-line>
        <view class="che">
          备注：
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
          识别结果：
          <text style="color: #37ad70;">{{recordInfo.resultName}}</text>
        </view>
        <u-line color="#2979ff"></u-line>
        <view class="che">
          判别概率：
          <text style="color: #37ad70;">{{recordInfo.resultValue*100}}%</text>
        </view>
        <u-line color="#2979ff"></u-line>
        
        <view class="che">
          取样日期：
          <text style="color: #37ad70;">{{recordInfo.createTime}}</text>
        </view>
        <u-line color="#2979ff"></u-line>
        
      </view>
      

    </view>


  </view>
</template>

<script>
  export default {
    data() {
      return {
        recordInfo: {},
        loading:true
      }
    },
    methods: {
      async getRecordInfo(id) {
        const {
          data: res
        } = await uni.$http.get('/record', {
          id: id
        })

        this.recordInfo = res.data
        this.loading = false

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
