import request from '@/utils/request'

//获取角色数据
export function getUserList(params) {
    return request({
        url: `/user/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}&keywords=${params.keywords}`,
        method: 'get',
    })
}