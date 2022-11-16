import request from '@/utils/request'

//获取用户列表
export function getUserList(params) {
    return request({
        url: `/user/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}&keywords=${params.keywords}`,
        method: 'get',
    })
}

//新增用户
export function addUser(data) {
    return request({
        url: '/user',
        method: 'post',
        data: data
    })
}

//修改用户
export function updateUser(data) {
    return request({
        url: '/user',
        method: 'put',
        data: data
    })
}

//根据id查询单个用户
export function getUserBy(id) {
    return request({
        url: '/user',
        method: 'get',
        params: id
    })
}

//删除用户
export function deleteUser(roleId){
    return request({
        url: `/user/${roleId}`,
        method: 'delete',
    })
}

//用户注销
export function userLogOut(){
    return request({
        url: `/user/logOut`,
        method: 'get',
    })
}