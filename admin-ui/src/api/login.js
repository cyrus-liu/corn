import request from '@/utils/request'

// 登录方法
export function login(userName, password) {
    const data = {
        userName,
        password
    }
    return request({
        url: '/user/login',
        method: 'post',
        data: data
    })
}



