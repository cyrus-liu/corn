<template>
  <view>
    <view class="img-container"></view>

    <view class="d-card">
      <u--form :model="recordInfo" ref="recordFrom" :rules="rules">
        <u-form-item label="样本名称" prop="name">
          <u-input v-model="recordInfo.name" />
        </u-form-item>

        <u-form-item label="样本备注" prop="remark">
          <u--textarea v-model="recordInfo.remark" count></u--textarea>
        </u-form-item>

        <u-form-item label="样本图像">
          <u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple :maxCount="1"
            width="550rpx" height="350rpx">
          </u-upload>
        </u-form-item>

        <u-form-item label="识别结果" v-if="AiResultList">
          <view class="res-list" v-for="(item,index) in AiResultList" :key="index">
            <view>{{item.name}}</view>
            <view>判别概率：{{ Number(item.value*100).toFixed(1)}}%</view>
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
        AiResultList: null,
        fileList1: [],
        rules: {
          name: [{
              required: true,
              message: '名称不得为空',
              trigger: ['blur', 'change']
            },
            {
              min: 3,
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
      reset() {
        this.recordInfo = {
          name: '', //标记昵称
          remark: '', //标记备注
          resultName: null, //识别结果
          resultValue: null, //相似度
          longitude: null, //经度
          latitude: null, //纬度
          imgUrl: null
        }

        this.AiResultList = null
        this.fileList1 = []
      },
      async submit() {
        try {
          const recordFrom = await this.$refs.recordFrom.validate()
          if (recordFrom) {

            if (this.recordInfo.imgUrl == null && this.recordInfo.resultName == null) {
              return uni.$u.toast('提交失败')
            }

            const {
              data: res
            } = await uni.$http.post('/record', this.recordInfo, {
              header: {
                "token": uni.getStorageSync('token')
              }
            })

            if (res.code == 200) {
              this.reset()

              uni.redirectTo({
                url: '/pages/home/home'
              });
            }

          }
        } catch (e) {
          uni.$u.toast('提交失败')
        }
      },
      // 删除图片
      deletePic(event) {
        this[`fileList${event.name}`].splice(event.index, 1)
        this.AiResultList = null
      },

      // 新增图片
      async afterRead(event) {
        // 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
        let lists = [].concat(event.file)
        let fileListLen = this[`fileList${event.name}`].length
        lists.map((item) => {
          this[`fileList${event.name}`].push({
            ...item,
            status: 'uploading',
            message: '处理中'
          })
        })

        const {
          data: res
        } = await this.uploadFilePromise(event.file[0].url)

        const result = JSON.parse(res)

        if (result.code == 200) {

          //处理完成
          let item = this[`fileList${event.name}`][fileListLen]
          this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
            status: 'success',
            message: '识别成功',
            url: result.data.imgUrl,
          }))
          this.recordInfo.imgUrl = result.data.imgUrl

          //===== 数据格式转换=====
          //对象转换数组
          let obj = result.data.aiResultVo.data
          let arr = []
          for (let i in obj) {
            let o = {};
            o[i] = obj[i];
            arr.push(o)
          }

          let resultArr = []
          arr.forEach(item => {
            let r = {
              name: Object.keys(item)[0],
              value: parseFloat(Object.values(item)[0])
            }
            resultArr.push(r)
          })

          function compare(p) { //这是比较函数
            return (m, n) => {
              let a = m[p];
              let b = n[p];
              return b - a;
            }
          }

          this.recordInfo.resultName = resultArr.sort(compare('value'))[0].name
          this.recordInfo.resultValue = resultArr.sort(compare('value'))[0].value

          this.AiResultList = resultArr.sort(compare('value'))

        }
      },

      uploadFilePromise(url) {
        //返回图片url
        return new Promise((resolve, reject) => {
          uni.uploadFile({
            url: 'http://www.abinya.top:8881/upload',
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

<style lang="scss">
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
    padding: 0 30rpx;
    display: flex;
    justify-content: space-between;
  }
</style>
