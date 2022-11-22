<template>
  <view>
    <view class="img-container"></view>

    <view class="d-card">
      <u--form :model="recordInfo" ref="recordFrom" :rules="rules">
        <u-form-item label="名称" prop="name">
          <u-input v-model="recordInfo.name" placeholder="样本名称" />
        </u-form-item>

        <u-form-item label="备注" prop="remark">
          <u--textarea v-model="recordInfo.remark" placeholder="样本备注" count></u--textarea>
        </u-form-item>

        <u-alert :title="title"  showIcon="true"  effect="light" type="warning"></u-alert>
        <u-form-item label="样本">
          <u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple :maxCount="1"
            width="550rpx" height="350rpx">
          </u-upload>
        </u-form-item>

        <u-form-item label="结果" v-if="aiRes">
          <view class="res-list">
            <view>{{aiRes.name}}</view>
            <view>判别概率：{{Number(aiRes.value*100).toFixed(2)}}%</view>
          </view>
        </u-form-item>

      </u--form>

      <u-button type="primary" text="确定" color="#198a71" @click="submit"></u-button>
    </view>


  </view>

</template>

<script>
  export default {
    data() {
      return {
        recordInfo: {
          //标记昵称
          name: '',
          //标记备注
          remark: '',
          //识别结果
          resultName: null,
          //相似度
          resultValue: null,
          //经度
          longitude: null,
          //纬度
          latitude: null,
          //图片url
          imgUrl: null
        },
        aiRes: null,
        title:'因成本原因，目前只支持识别：雏菊、蒲公英、玫瑰、向日葵、郁金香；不支持PNG格式的图片',
        fileList1: [],
        rules: {
          name: [{
              required: true,
              message: '名称不得为空',
              trigger: ['blur', 'change']
            },
            {
              min: 2,
              max: 20,
              message: '长度在3-20个字符之间'
            }
          ],

          remark: [{
            required: true,
            message: '备注不得为空',
            trigger: ['blur', 'change']
          }, {
            min: 0,
            max: 140,
            message: '长度在0-140个字符之间'
          }],
          img: [{
            required: true,
            message: '图片不能为空',
            trigger: ['blur', 'change']
          }],
        },
      };
    },
    methods: {
      //提交样本
      async submit() {
        try {
          const recordFrom = await this.$refs.recordFrom.validate()
          if (recordFrom) {

            if (this.recordInfo.imgUrl == null && this.recordInfo.resultName == null) {
              return uni.$u.toast('图片还没识别完！')
            }

            const {
              data: res
            } = await uni.$http.post('/record', this.recordInfo, {
              header: {
                "token": uni.getStorageSync('token')
              }
            })

            if (res.code == 200) {
              uni.redirectTo({
                url: '/subpkg/myRecord/myRecord'
              });
            }

          }
        } catch (e) {
          uni.$u.toast('表单还没填完！')
        }

      },
      // 删除图片
      deletePic(event) {
        this[`fileList${event.name}`].splice(event.index, 1)
        this.aiRes = null
      },

      // 新增图片
      async afterRead(event) {

        //TODO 过滤png图片

        // 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
        let lists = [].concat(event.file)
        let fileListLen = this[`fileList${event.name}`].length
        lists.map((item) => {
          this[`fileList${event.name}`].push({
            ...item
          })
        })

        const {
          data: res
        } = await this.uploadFilePromise(event.file[0].url)

        const result = JSON.parse(res)

        if (result.code != 200) return uni.$u.toast('识别失败：' + result.msg)

        //处理完成
        let item = this[`fileList${event.name}`][fileListLen]
        this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
          url: result.data.imgUrl
        }))

        this.recordInfo.imgUrl = result.data.imgUrl

        let aiData = result.data.aiResultVo.data

        //找出最大值
        let max = parseFloat(aiData[0].value).toFixed(2)
        let aiRes = null
        aiData.forEach(item => {
          if (max < parseFloat(item.value).toFixed(2)) {
            max = parseFloat(item.value).toFixed(2)
            aiRes = item
          }
        })

        //过滤低于80%的结果
        if (parseFloat(aiRes.value).toFixed(2) < parseFloat("0.6099").toFixed(2)) {
          uni.$u.toast('识别度低于60%，不能保证正确结果，换张图片试试！')
          return
        }

        this.recordInfo.resultName = aiRes.name
        this.recordInfo.resultValue = aiRes.value

        this.aiRes = aiRes
      },

      uploadFilePromise(url) {
        //返回图片url
        return new Promise((resolve, reject) => {
          uni.uploadFile({
            // url: 'https://www.abinya.top/upload',
            url: 'http://127.0.0.1:8881/upload',
            filePath: url,
            name: 'file',
            //token校验
            header: {
              "token": uni.getStorageSync('token')
            },
            success: (res) => {
              if (res) {
                resolve(res)
              }
            }
          });
        })
      },
    },

    onShow() {
      this.recordInfo.latitude = this.$store.state.latitude
      this.recordInfo.longitude = this.$store.state.longitude
      this.$refs['recordFrom'].clearValidate();
    },

    onReady() {
      //如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则。
      this.$refs.recordFrom.setRules(this.rules)
    }
  }
</script>

<style lang="less">
  .img-container {
    width: 100%;
    height: 368px;
    background-color: #149a84;
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

  .res-list {
    display: flex;
    justify-content: space-between;
  }
</style>
