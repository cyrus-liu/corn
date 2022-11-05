<template>
  <view>
    <view class="img-container">
      <image style="width: 100%; height: 100%;"
        src="https://vkceyugu.cdn.bspapp.com/VKCEYUGU-8059ac71-13c5-4a15-bec8-3ac9f98f0840/8fbf8ef2-7bca-4675-9dab-32c5a0647744.jpg">
      </image>
    </view>

    <view class="d-card">
      <u--form :model="recordInfo" ref="uForm">
        <u-form-item label="名称" prop="name">
          <u-input v-model="recordInfo.name" />
        </u-form-item>

        <u-form-item label="备注" prop="remark">
          <u--textarea v-model="recordInfo.remark" placeholder="请输入内容" count></u--textarea>
        </u-form-item>
        
        <u-form-item label="识别图像" >
          <u-upload :fileList="fileList" @afterRead="afterRead" @delete="deletePic" name="6" multiple :maxCount="1"
            width="100%" height="150">
            
          </u-upload>
        </u-form-item>

        

      </u--form>
      <u-button type="primary" text="确定"></u-button>
    </view>

  </view>

</template>

<script>
  export default {
    data() {
      return {
        recordInfo: {
          name: '',
          remark: '',
          fileList: []
        }
      };
    }
  }
</script>

<style lang="scss">
  .img-container {
    width: 100%;
    height: 368px;
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
</style>
