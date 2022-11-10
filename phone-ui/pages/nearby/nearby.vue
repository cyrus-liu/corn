<template>
  <view>
    <view class="img-container">
      <image style="width: 100%; height: 100%;"
        src="https://vkceyugu.cdn.bspapp.com/VKCEYUGU-8059ac71-13c5-4a15-bec8-3ac9f98f0840/8fbf8ef2-7bca-4675-9dab-32c5a0647744.jpg">
      </image>
    </view>

    <view class="d-card">
      <u--form :model="recordInfo" ref="recordFrom" :rules="rules">
        <u-form-item label="名称" prop="name">
          <u-input v-model="recordInfo.name" />
        </u-form-item>

        <u-form-item label="备注" prop="remark">
          <u--textarea v-model="recordInfo.remark" count></u--textarea>
        </u-form-item>

        <u-form-item label="识别图像">
          <u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple :maxCount="1"
            width="550rpx" height="350rpx">
          </u-upload>
        </u-form-item>
      </u--form>

      <u-button type="primary" text="确定" @click="submit"></u-button>
    </view>


  </view>

</template>

<script>
  export default {
    data() {
      return {
        recordInfo: {
          name: '', //标记昵称
          remark: '', //标记备注
        },
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
            min: 3,
            max: 140,
            message: '长度在3-140个字符之间'
          }]
        },
      };
    },
    methods: {
      submit() {
        this.$refs.recordFrom.validate().then(res => {
          uni.$u.toast('校验通过')

        }).catch(errors => {
          uni.$u.toast('校验失败')

        })
      },
      // 删除图片
      deletePic(event) {
        this[`fileList${event.name}`].splice(event.index, 1)
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


        //上传图片请求
        console.log(event.file[0].url);


        // const {
        //   data: res
        // } = await uni.$http.post('/record/ai')

        // if (res.meta.status !== 200) {
        //   return uni.showToast({
        //     title: '数据请求失败！',
        //     duration: 1500,
        //     icon: 'none',
        //   })
        // }

        this.uploadFilePromise(event.file[0].url)


        console.log(res);


        //处理完成
        let item = this[`fileList${event.name}`][fileListLen]
        this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
          status: 'success',
          message: '',
          url: ''
        }))
      },

      uploadFilePromise(url) {
        //返回图片url
        return new Promise((resolve, reject) => {
          let a = uni.uploadFile({
            url: 'http://localhost:8881/upload', // 仅为示例，非真实的接口地址
            filePath: url,
            name: 'file',
            // header: {
            //   "Authorization": "bearer ASDFGHJKL1314510" //token校验
            // },
            formData: {
              user: 'test'
            },
            success: (res) => {
              setTimeout(() => {
                resolve(res.data.data)
                //JSON.parse(res.data) //字符串转对象
              }, 1000)
            }
          });
        })
      },
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
