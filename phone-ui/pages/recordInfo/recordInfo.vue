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
        <text style="color: #37ad70;">{{rrecordInfo.resultName}}</text>
      </view>
      <u-line color="#2979ff"></u-line>
      <view class="che">
        相似度：
         <text style="color: #37ad70;">{{recordInfo.resultValue}}</text>
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
      getRecordInfo(id) {
        uni.$http.get('/record', {
          id: id
        }).then(res => {
          if (res.data.code != 200) return uni.$u.toast('获取数据失败')
          this.recordInfo = res.data.data
        })

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
