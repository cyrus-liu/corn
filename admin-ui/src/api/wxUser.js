import request from '@/utils/request'

//获取用户列表
export function getWxUserDataList(params) {
    return request({
        url: `/wxUser/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}&keywords=${params.keywords}`,
        method: 'get',
    })
}


//修改用户
export function updateWxUser(data) {
    return request({
        url: '/wxUser',
        method: 'put',
        data: data
    })
}