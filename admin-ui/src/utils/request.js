import axios from "axios";
import store from '@/store'
import { Notification, Message } from 'element-ui'


//创建一个实例
const service = axios.create({
    // baseURL: 'http://www.abinya.top:8881/', //服务器基础路径
    baseURL: process.env.VUE_APP_BASE_API, //服务器基础路径
    timeout: 4000, //允许超时的时间
});


// 请求拦截器
service.interceptors.request.use(function (config) {

    //获取token，每次请求携带token
    let token = store.state.token

    if (token !== null) {
        config.headers.token = token
    }

    // 在发送请求之前做些什么
    return config;
}, error => {
    // 对请求错误做些什么
    return Promise.reject(error);
});

//响应拦截器
service.interceptors.response.use(config => {

    if(config.data.code !== 200){
        Notification.error({
            title: '错误',
            message: config.data.msg
        });
        return Promise.reject('error')
    }

    // 在发送请求之前做些什么
    return config
}, error => {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
})


export default service;