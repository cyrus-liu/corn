<template>
  <div class="container">

    <div style="display: flex;justify-content: center;margin-top: 200px">

      <el-card style="width: 380px">
        <div slot="header">
          <span style="font-size: 24px;font-weight: 800">后台管理系统</span>
        </div>


        <el-form :model="user" :rules="rules" ref="userForm" label-width="50px" hide-required-asterisk>
          <el-form-item label="账号" prop="username">
            <el-input
                v-model="user.username"
                clearable
            ></el-input>
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input
                type="password"
                v-model="user.password"
                @keydown.enter.native="doLogin"
                clearable
            ></el-input>
          </el-form-item>

          <el-button style="width: 100%" type="primary" @click="submitForm('userForm')">
            登录系统
          </el-button>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>

import {login} from "@/api/login";

export default {
  name: "Login",
  data() {
    return {
      user: {
        username: "nonghb", //账号
        password: "123456", //密码
      },

      //登录表单验证对象
      rules: {
        username: [
          {required: true, message: '用户名不得为空', trigger: 'blur'},
          {min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不得为空', trigger: 'blur'},
          {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
        ]
      }
    }
  },

  methods: {
    //登录功能
    async doLogin() {
      const {data: res} = await login(this.user.username, this.user.password)

      //保存token
      this.$store.commit('setToken', res.data)
      //加载用户菜单
      // getMenu()

      this.$message.success("登录成功");
      await this.$router.push({path: "/"});
    },

    //表单校验
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.doLogin()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //清除校验
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped lang="less">
.container {
  height: 100%;
  overflow: hidden;
}
</style>